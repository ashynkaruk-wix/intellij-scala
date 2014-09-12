package org.jetbrains.plugins.scala
package lang.refactoring.changeSignature

import com.intellij.psi._
import com.intellij.refactoring.changeSignature._
import com.intellij.usageView.UsageInfo
import org.jetbrains.plugins.scala.codeInsight.intention.types.Update
import org.jetbrains.plugins.scala.extensions.ElementText
import org.jetbrains.plugins.scala.lang.psi.ScalaPsiUtil
import org.jetbrains.plugins.scala.lang.psi.api.base.ScReferenceElement
import org.jetbrains.plugins.scala.lang.psi.api.expr._
import org.jetbrains.plugins.scala.lang.psi.api.statements._
import org.jetbrains.plugins.scala.lang.psi.api.statements.params.ScClassParameter
import org.jetbrains.plugins.scala.lang.psi.api.toplevel.{ScModifierListOwner, ScNamedElement}
import org.jetbrains.plugins.scala.lang.psi.impl.ScalaPsiElementFactory
import org.jetbrains.plugins.scala.lang.psi.types.{JavaArrayType, ScType}
import org.jetbrains.plugins.scala.lang.refactoring.changeSignature.changeInfo.ScalaChangeInfo
import org.jetbrains.plugins.scala.lang.refactoring.extractMethod.ScalaExtractMethodUtils

import scala.collection.mutable.ListBuffer

/**
 * Nikolay.Tropin
 * 2014-08-13
 */
private[changeSignature] trait ScalaChangeSignatureUsageHandler {

  protected def handleChangedName(change: ChangeInfo, usage: UsageInfo): Unit = {
    if (!change.isNameChanged) return

    val nameId = usage match {
      case ScalaNamedElementUsageInfo(scUsage) => scUsage.namedElement.nameId
      case MethodCallUsageInfo(ref, _) => ref.nameId
      case RefExpressionUsage(r) => r.nameId
      case InfixExprUsageInfo(i) => i.operation.nameId
      case PostfixExprUsageInfo(p) => p.operation.nameId
      case MethodValueUsageInfo(und) => und.bindingExpr match {
        case Some(r: ScReferenceExpression) => r.nameId
        case _ => null
      }
      case _ => null
    }
    if (nameId == null) return

    val newName = change.getNewName
    replaceNameId(nameId, newName)
  }

  protected def handleVisibility(change: ChangeInfo, usage: ScalaNamedElementUsageInfo): Unit = {
    val visibility = change match {
      case j: JavaChangeInfo => j.getNewVisibility
      case _ => return
    }
    val member = ScalaPsiUtil.nameContext(usage.namedElement) match {
      case m: ScModifierListOwner => m
      case _ => return
    }

    ScalaPsiUtil.changeVisibility(member, visibility)
  }

  protected def handleReturnTypeChange(change: ChangeInfo, usage: ScalaNamedElementUsageInfo): Unit = {
    val element = usage.namedElement
    if (!change.isReturnTypeChanged) return

    val substType: ScType = UsageUtil.returnType(change, usage) match {
      case Some(result) => result
      case None => return
    }
    val newTypeElem = ScalaPsiElementFactory.createTypeElementFromText(substType.canonicalText, element.getManager)

    val oldTypeElem = element match {
      case fun: ScFunction => fun.returnTypeElement
      case ScalaPsiUtil.inNameContext(pd: ScPatternDefinition) => pd.typeElement
      case ScalaPsiUtil.inNameContext(vd: ScVariableDefinition) => vd.typeElement
      case cp: ScClassParameter => cp.typeElement
      case _ => None
    }
    oldTypeElem match {
      case Some(te) =>
        val replaced = te.replace(newTypeElem)
        ScalaPsiUtil.adjustTypes(replaced)
      case None =>
        val (context, anchor) = ScalaPsiUtil.nameContext(element) match {
          case f: ScFunction => (f, f.paramClauses)
          case p: ScPatternDefinition => (p, p.pList)
          case v: ScVariableDefinition => (v, v.pList)
          case cp: ScClassParameter => (cp.getParent, cp)
          case ctx => (ctx, ctx.getLastChild)
        }
        Update.addTypeAnnotation(substType, context, anchor)
    }
  }

  protected def handleParametersUsage(change: ChangeInfo, usage: ParameterUsageInfo): Unit = {
    if (change.isParameterNamesChanged || change.isParameterSetOrOrderChanged) {
      replaceNameId(usage.ref.getElement, usage.newName)
    }
  }

  protected def handleChangedParameters(change: ChangeInfo, usage: ScalaNamedElementUsageInfo): Unit = {
    if (!change.isParameterNamesChanged && !change.isParameterSetOrOrderChanged && !change.isParameterTypesChanged) return

    def inner(named: ScNamedElement): Unit = {
      val (keywordToChange, paramClauses) = named match {
        case f: ScFunction => (None, Some(f.paramClauses))
        case ScalaPsiUtil.inNameContext(pd: ScPatternDefinition) if pd.isSimple => (Some(pd.valKeyword), None)
        case ScalaPsiUtil.inNameContext(vd: ScVariableDefinition) if vd.isSimple => (Some(vd.varKeyword), None)
        case _ => return
      }
      val defKeyword = ScalaPsiElementFactory.createMethodFromText("def foo {}", named.getManager).children.find(_.getText == "def").get
      if (change.getNewParameters.length > 0) keywordToChange.foreach(_.replace(defKeyword))

      val paramsText = parameterListText(change, usage)
      val nameId = named.nameId
      val newClauses = ScalaPsiElementFactory.createParamClausesWithContext(paramsText, named, nameId)
      val result = paramClauses match {
        case Some(p) => p.replace(newClauses)
        case None => nameId.getParent.addAfter(newClauses, nameId)
      }
      ScalaPsiUtil.adjustTypes(result)
    }

    inner(usage.namedElement)
  }

  protected def handleUsageArguments(change: ChangeInfo, usage: UsageInfo): Unit = {
    usage match {
      case m: MethodCallUsageInfo => handleMethodCallUsagesArguments(change, m)
      case r: RefExpressionUsage => handleRefUsageArguments(change, r)
      case i: InfixExprUsageInfo => handleInfixUsage(change, i)
      case p: PostfixExprUsageInfo => handlePostfixUsage(change, p)
      case _ =>
    }
  }

  protected def handleInfixUsage(change: ChangeInfo, usage: InfixExprUsageInfo): Unit = {
    val infix = usage.infix
    val newParams = change.getNewParameters
    if (newParams.length != 1) {
      infix.getArgExpr match {
        case t: ScTuple =>
          val tupleText = arguments(change, usage).map(_.getText).mkString("(", ", ", ")")
          val newTuple = ScalaPsiElementFactory.createExpressionWithContextFromText(tupleText, infix, t)
          t.replaceExpression(newTuple, removeParenthesis = false)
        case _ =>
          val methodCall = ScalaPsiElementFactory.createEquivMethodCall(infix)
          val argList = createArgList(change, usage)
          methodCall.args.replace(argList)
          infix.replaceExpression(methodCall, removeParenthesis = true)
      }
    } else {
      val paramExpr = arguments(change, usage).headOption match {
        case Some(expr) => expr
        case None => ScalaPsiElementFactory.createExpressionWithContextFromText("()", infix, infix.getArgExpr)
      }
      infix.getArgExpr.replaceExpression(paramExpr, removeParenthesis = true)
    }
  }

  protected def handleRefUsageArguments(change: ChangeInfo, usage: RefExpressionUsage): Unit = {
    if (change.getNewParameters.isEmpty) return

    val ref = usage.refExpr
    val argList = createArgList(change, usage)
    ref.addAfter(argList, ref.nameId)
  }

  protected def handlePostfixUsage(change: ChangeInfo, usage: PostfixExprUsageInfo): Unit = {
    if (change.getNewParameters.isEmpty) return

    val qualRef = ScalaPsiElementFactory.createEquivQualifiedReference(usage.postfix)
    val argList = createArgList(change, usage)
    qualRef.addAfter(argList, qualRef.nameId)
    usage.postfix.replaceExpression(qualRef, removeParenthesis = true)
  }

  protected def handleMethodCallUsagesArguments(change: ChangeInfo, usage: MethodCallUsageInfo): Unit = {
    val call = usage.call
    val argList = createArgList(change, usage)
    call.args.replace(argList)
  }

  private def createArgList(change: ChangeInfo, methodUsage: MethodUsageInfo): ScArgumentExprList = {
    val args: Seq[ScExpression] = arguments(change, methodUsage)
    val argText = args.map(_.getText).mkString("(", ", ", ")")
    ScalaPsiElementFactory.createExpressionFromText(s"foo$argText", methodUsage.expr.getManager)
            .children.collectFirst{case al: ScArgumentExprList => al}.get
  }

  private def arguments(change: ChangeInfo, methodUsage: MethodUsageInfo): Seq[ScExpression] = {
    if (change.getNewParameters.length == 0) return Seq.empty

    val oldArgsInfo = methodUsage.argsInfo

    nonVarargArgs(change, oldArgsInfo) ++: varargsExprs(change, oldArgsInfo, methodUsage.expr)
  }

  private def newArgumentExpression(argsInfo: OldArgsInfo,
                                    newParam: ParameterInfo,
                                    manager: PsiManager,
                                    addDefaultArg: Boolean,
                                    named: Boolean): Option[ScExpression] = {

    val oldIdx = newParam.getOldIndex

    if (oldIdx < 0 && addDefaultArg) return None

    val default = newParam.getDefaultValue

    val withoutName =
      if (oldIdx < 0) {
        if (!default.isEmpty) default else "???"
      }
      else {
        argsInfo.byOldParameterIndex.get(oldIdx) match {
          case None => return None
          case Some(seq) if seq.size > 1 => return None
          case Some(Seq(named: ScAssignStmt)) => return Some(named)
          case Some(Seq(expr)) => expr.getText
        }
      }
    val argText = if (named) s"${newParam.getName} = $withoutName" else withoutName
    Some(ScalaPsiElementFactory.createExpressionFromText(argText, manager))
  }

  private def replaceNameId(elem: PsiElement, newName: String) {
    elem match {
      case scRef: ScReferenceElement =>
        val newId = ScalaPsiElementFactory.createIdentifier(newName, scRef.getManager).getPsi
        scRef.nameId.replace(newId)
      case jRef: PsiReferenceExpression =>
        jRef.getReferenceNameElement match {
          case nameId: PsiIdentifier =>
            val factory: PsiElementFactory = JavaPsiFacade.getInstance(jRef.getProject).getElementFactory
            val newNameIdentifier: PsiIdentifier = factory.createIdentifier(newName)
            nameId.replace(newNameIdentifier)
          case _ =>
        }
      case _ =>
        elem.replace(ScalaPsiElementFactory.createIdentifier(newName, elem.getManager).getPsi)
    }
  }

  private def parameterListText(change: ChangeInfo, usage: ScalaNamedElementUsageInfo): String = {
    def paramType(paramInfo: ParameterInfo) = {
      val method = change.getMethod
      paramInfo match {
        case sInfo: ScalaParameterInfo =>
          val text = UsageUtil.substitutor(usage).subst(sInfo.scType).canonicalText
          val `=> ` = if (sInfo.isByName) ScalaPsiUtil.functionArrow(method.getProject) + " " else ""
          val `*` = if (sInfo.isRepeatedParameter) "*" else ""
          `=> ` + text + `*`
        case jInfo: JavaParameterInfo =>
          val javaType = jInfo.createType(method, method.getManager)
          val scType = UsageUtil.substitutor(usage).subst(ScType.create(javaType, method.getProject))
          (scType, javaType) match {
            case (JavaArrayType(tpe), _: PsiEllipsisType) => tpe.canonicalText + "*"
            case _ => scType.canonicalText
          }
        case info => info.getTypeText
      }
    }
    def scalaDefaultValue(paramInfo: ParameterInfo): Option[String] = {
      val oldIdx = paramInfo.getOldIndex
      if (oldIdx >= 0) usage.defaultValues(oldIdx)
      else change match {
        case sc: ScalaChangeInfo if sc.isAddDefaultArgs =>
          paramInfo.getDefaultValue match {
            case "" | null => Some("???")
            case s => Some(s)
          }
        case _ => None
      }
    }

    val paramInfos = change.getNewParameters.toSeq
    val project = change.getMethod.getProject
    val params = paramInfos.map { p =>
      val typedName = ScalaExtractMethodUtils.typedName(p.getName, paramType(p), project, byName = false)
      val default = scalaDefaultValue(p).fold("")(" = " + _)
      typedName + default
    }
    params.mkString("(", ", ", ")")
  }

  private def nonVarargArgs(change: ChangeInfo, oldArgsInfo: OldArgsInfo): Seq[ScExpression] = {
    def isRepeated(p: ParameterInfo) = p match {
      case p: ScalaParameterInfo => p.isRepeatedParameter
      case p: JavaParameterInfo => p.isVarargType
      case _ => false
    }

    val isAddDefault = change match {
      case c: ScalaChangeInfo => c.isAddDefaultArgs
      case c: JavaChangeInfo => c.isGenerateDelegate
      case _ => true
    }

    val manager = change.getMethod.getManager
    var needNamed = false
    val buffer = new ListBuffer[ScExpression]
    for {
      (param, idx) <- change.getNewParameters.toSeq.zipWithIndex
      if !isRepeated(param)
    } {
      newArgumentExpression(oldArgsInfo, param, manager, isAddDefault, needNamed) match {
        case Some(expr) =>
          buffer += expr
          if (expr.isInstanceOf[ScAssignStmt] && idx > buffer.size - 1) needNamed = true
        case None => needNamed = true
      }
    }
    buffer.toSeq
  }

  private def varargsExprs(changeInfo: ChangeInfo, argsInfo: OldArgsInfo, context: PsiElement): Seq[ScExpression] = {
    val parameters = changeInfo.getNewParameters

    val param = parameters.last
    param match {
      case s: ScalaParameterInfo if s.isRepeatedParameter =>
      case j: JavaParameterInfo if j.isVarargType =>
      case _ => return Seq.empty
    }
    val oldIndex = param.getOldIndex
    changeInfo match {
      case jChangeInfo: JavaChangeInfo =>
        if (oldIndex < 0) {
          val text = param.getDefaultValue
          if (text != "") Seq(ScalaPsiElementFactory.createExpressionWithContextFromText(text, context, context))
          else Seq.empty
        }
        else {
          val (argExprs, wasNamed) = argsInfo.byOldParameterIndex.get(oldIndex) match {
            case Some(Seq(ScAssignStmt(_, Some(expr)))) => (Seq(expr), true)
            case Some(seq) => (seq, false)
            case _ => return Seq.empty
          }
          if (jChangeInfo.isArrayToVarargs) {
            argExprs match {
              case Seq(ScMethodCall(ElementText("Array"), arrayArgs)) => arrayArgs
              case Seq(expr) =>
                val typedText = ScalaExtractMethodUtils.typedName(expr.getText, "_*", expr.getProject, byName = false)
                val naming = if (wasNamed) param.getName + " = " else ""
                val text = naming + typedText
                val typedExpr = ScalaPsiElementFactory.createExpressionWithContextFromText(text, expr.getContext, expr)
                Seq(typedExpr)
            }
          }
          else argExprs
        }
      case jChangeInfo: JavaChangeInfo if jChangeInfo.isRetainsVarargs =>
        argsInfo.byOldParameterIndex.getOrElse(oldIndex, Seq.empty)
      case _ => Seq.empty
    }
  }
}
