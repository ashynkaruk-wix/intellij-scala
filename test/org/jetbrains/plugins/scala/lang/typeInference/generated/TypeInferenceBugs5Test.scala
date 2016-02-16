package org.jetbrains.plugins.scala.lang.typeInference
package generated

import com.intellij.openapi.extensions.Extensions
import org.jetbrains.plugins.scala.lang.psi.impl.toplevel.typedef.SyntheticMembersInjector
import org.jetbrains.plugins.scala.lang.typeInference.testInjectors.{SCL9533Injector, SCL9865Injector, SCL9445Injector}

class TypeInferenceBugs5Test extends TypeInferenceTestBase {
  //This class was generated by build script, please don't change this
  override def folderPath: String = super.folderPath + "bugs5/"

  def testAnyPatternMatching(): Unit = doTest()

  def testAssignmentNotImported(): Unit = doTest()

  def testCloseable(): Unit = doTest()

  def testCompoundTypeConformance(): Unit = doTest()

  def testCompoundTypeUnapply(): Unit = doTest()

  def testCyclicGetClass(): Unit = doTest()

  def testDeeperLub(): Unit = doTest()

  def testDefaultParamInference(): Unit = doTest()

  def testEA52539(): Unit = doTest()

  def testExistentialConformance(): Unit = doTest()

  def testExistentialConformance2(): Unit = doTest()

  def testExpectedOption(): Unit = doTest()

  def testFakePrimitiveConversion(): Unit = doTest()

  def testForStmtBug(): Unit = doTest()

  def testImplicitClause(): Unit = doTest()

  def testImplicitlyAddedExtractor(): Unit = doTest()

  def testImplicitTest(): Unit = doTest()

  def testImplicitVsNone(): Unit = { doTest() }

  def testInfixApply(): Unit = doTest()

  def testJavaArrayType(): Unit = doTest()

  def testParametersLub(): Unit = doTest()

  def testParenthesisedUnderscore(): Unit = doTest()

  def testParenthesisedUnderscore2(): Unit = doTest()

  def testPatternInterpolation(): Unit = doTest()

  def testRecursiveFunction(): Unit = doTest()

  def testRepeatedParams(): Unit = doTest()

  def testRepeatedParamsResolve(): Unit = doTest()

  def testSCL1971(): Unit = doTest()

  def testSCL2055(): Unit = doTest()

  def testSCL2292(): Unit = doTest()

  def testSCL2929(): Unit = doTest()

  def testSCL2936(): Unit = doTest()

  def testSCL3052(): Unit = doTest()

  def testSCL3074(): Unit = doTest()

  def testSCL3288(): Unit = doTest()

  def testSCL2381A(): Unit = doTest()

  def testSCL2381B(): Unit = doTest()

  def testSCL2381C(): Unit = doTest()

  def testSCL2381D(): Unit = doTest()

  def testSCL2426(): Unit = doTest()

  def testSCL2480(): Unit = doTest()

  def testSCL2487(): Unit = doTest()

  def testSCL2493(): Unit = doTest()

  def testSCL2618(): Unit = doTest()

  def testSCL2656(): Unit = doTest()

  def testSCL2777A(): Unit = doTest()

  def testSCL2777B(): Unit = doTest()

  def testSCL3063(): Unit = doTest()

  def testSCL2806(): Unit = doTest()

  def testSCL2806B(): Unit = doTest()

  def testSCL2817(): Unit = doTest()

  def testSCL2820(): Unit = doTest()

  def testSCL2889(): Unit = doTest()

  def testSCL2893(): Unit = doTest()

  def testSCL3076(): Unit = doTest()

  def testSCL3123(): Unit = doTest()

  def testSCL3213(): Unit = doTest()

  def testSCL3216(): Unit = doTest()

  def testSCL3275(): Unit = doTest()

  def testSCL3277(): Unit = doTest()

  def testSCL3278A(): Unit = doTest()

  def testSCL3328(): Unit = doTest()

  def testSCL3329(): Unit = doTest()

  def testSCL3330(): Unit = doTest()

  def testSCL3338(): Unit = doTest()

  def testSCL3343(): Unit = doTest()

  def testSCL3347(): Unit = doTest()

  def testSCL3351(): Unit = doTest()

  def testSCL3354(): Unit = doTest()

  def testSCL3367(): Unit = doTest()

  def testSCL3590(): Unit = doTest()

  def testSCL3372(): Unit = doTest()

  def testSCL3385(): Unit = doTest()

  def testSCL3394(): Unit = doTest()

  def testSCL3412(): Unit = doTest()

  def testSCL3414A(): Unit = doTest()

  def testSCL3414B(): Unit = doTest()

  def testSCL3414C(): Unit = doTest()

  def testSCL3414D(): Unit = doTest()

  def testSCL3414E(): Unit = doTest()

  def testSCL3414F(): Unit = doTest()

  def testSCL3414G(): Unit = doTest()

  def testSCL3426(): Unit = doTest()

  def testSCL3427(): Unit = doTest()

  def testSCL3429(): Unit = doTest()

  def testSCL3455(): Unit = doTest()

  def testSCL3460(): Unit = doTest()

  def testSCL3468(): Unit = doTest()

  def testSCL3470(): Unit = doTest()

  def testSCL3482(): Unit = doTest()

  def testSCL3487(): Unit = doTest()

  def testSCL3496(): Unit = doTest()

  def testSCL3512(): Unit = doTest()

  def testSCL3517A(): Unit = doTest()

  def testSCL3517B(): Unit = doTest()

  def testSCL3517C(): Unit = doTest()

  def testSCL3537(): Unit = doTest()

  def testSCL3540(): Unit = doTest()

  def testSCL3542(): Unit = doTest()

  def testSCL3544(): Unit = doTest()

  def testSCL3549A(): Unit = doTest()

  def testSCL3549B(): Unit = doTest()

  def testSCL3552(): Unit = doTest()

  def testSCL3555(): Unit = doTest()

  def testSCL3565(): Unit = doTest()

  def testSCL3567(): Unit = doTest()

  def testSCL3603A(): Unit = doTest()

  def testSCL3603B(): Unit = doTest()

  def testSCL3654(): Unit = doTest()

  def testSCL3654B(): Unit = doTest()

  def testSCL3730(): Unit = doTest()

  def testSCL3735(): Unit = doTest()

  def testSCL3738(): Unit = doTest()

  def testSCL3766(): Unit = doTest()

  def testSCL3796(): Unit = doTest()

  def testSCL3801A(): Unit = doTest()

  def testSCL3801B(): Unit = doTest()

  def testSCL3817(): Unit = doTest()

  def testSCL3833(): Unit = doTest()

  def testSCL3834(): Unit = doTest()

  def testSCL3845(): Unit = doTest()

  def testSCL3854(): Unit = doTest()

  def testSCL3865(): Unit = doTest()

  def testSCL3877(): Unit = doTest()

  def testSCL3893(): Unit = doTest()

  def testSCL3908A(): Unit = doTest()

  def testSCL3908B(): Unit = doTest()

  def testSCL3912(): Unit = doTest()

  def testSCL3975(): Unit = doTest()

  def testSCL4031(): Unit = doTest()

  def testSCL4040(): Unit = doTest()

  def testSCL4052(): Unit = doTest()

  def testSCL4065(): Unit = doTest()

  def testSCL4077(): Unit = doTest()

  def testSCL4092(): Unit = doTest()

  def testSCL4093(): Unit = doTest()

  def testSCL4095A(): Unit = doTest()

  def testSCL4095B(): Unit = doTest()

  def testSCL4095C(): Unit = doTest()

  def testSCL4095D(): Unit = doTest()

  def testSCL4095E(): Unit = doTest()

  def testSCL4139(): Unit = doTest()

  def testSCL4150A(): Unit = doTest()

  def testSCL4150B(): Unit = doTest()

  def testSCL4150C(): Unit = doTest()

  def testSCL4150D(): Unit = doTest()

  def testSCL4158(): Unit = doTest()

  def testSCL4163A(): Unit = doTest()

  def testSCL4163B(): Unit = doTest()

  def testSCL4163C(): Unit = doTest()

  def testSCL4163D(): Unit = doTest()

  def testSCL4163E(): Unit = doTest()

  def testSCL4163F(): Unit = doTest()

  def testSCL4163G(): Unit = doTest()

  def testSCL4169(): Unit = doTest()

  def testSCL4186(): Unit = doTest()

  def testSCL4200(): Unit = doTest()

  def testSCL4276(): Unit = doTest()

  def testSCL4282(): Unit = doTest()

  def testSCL4293(): Unit = doTest()

  def testSCL4312(): Unit = doTest()

  def testSCL4321(): Unit = doTest()

  def testSCL4324(): Unit = doTest()

  def testSCL4353A(): Unit = doTest()

  def testSCL4353B(): Unit = doTest()

  def testSCL4353C(): Unit = doTest()

  def testSCL4354(): Unit = doTest()

  def testSCL4357(): Unit = doTest()

  def testSCL4357B(): Unit = doTest()

  def testSCL4363A(): Unit = doTest()

  def testSCL4363B(): Unit = doTest()

  def testSCL4375(): Unit = doTest()

  def testSCL4380(): Unit = doTest()

  def testSCL4389(): Unit = doTest()

  def testSCL4416(): Unit = doTest()

  def testSCL4432(): Unit = doTest()

  def testSCL4451(): Unit = doTest()

  def testSCL4478(): Unit = doTest()

  def testSCL4482(): Unit = doTest()

  def testSCL4493(): Unit = doTest()

  def testSCL4513(): Unit = doTest()

  def testSCL4545(): Unit = doTest()

  def testSCL4558(): Unit = doTest()

  def testSCL4559A(): Unit = doTest()

  def testSCL4559B(): Unit = doTest()

  def testSCL4589(): Unit = doTest()

  def testSCL4617(): Unit = doTest()

  def testSCL4650(): Unit = doTest()

  def testSCL4651(): Unit = doTest()

  def testSCL4656(): Unit = doTest()

  def testSCL4685(): Unit = doTest()

  def testSCL4695(): Unit = doTest()

  def testSCL4718(): Unit = doTest()

  def testSCL4740(): Unit = doTest()

  def testSCL4749(): Unit = doTest()

  def testSCL4801(): Unit = doTest()

  def testSCL4807(): Unit = doTest()

  def testSCL4809(): Unit = doTest()

  def testSCL4823(): Unit = doTest()

  def testSCL4891A(): Unit = doTest()

  def testSCL4897(): Unit = doTest()

  def testSCL4904(): Unit = doTest()

  def testSCL4938(): Unit = doTest()

  def testSCL4981(): Unit = doTest()

  def testSCL5023(): Unit = doTest()

  def testSCL5029(): Unit = doTest()

  def testSCL5030(): Unit = doTest()

  def testSCL5033(): Unit = doTest()

  def testSCL5048(): Unit = doTest()

  def testSCL5048B(): Unit = doTest()

  def testSCL5055(): Unit = doTest()

  def testSCL5060(): Unit = doTest()

  def testSCL5081(): Unit = doTest()

  def testSCL5104(): Unit = doTest()

  def testSCL5144(): Unit = doTest()

  def testSCL5159(): Unit = doTest()

  def testSCL5180(): Unit = doTest()

  def testSCL5185(): Unit = doTest()

  def testSCL5192(): Unit = doTest()

  def testSCL5193(): Unit = doTest()

  def testSCL5197(): Unit = doTest()

  def testSCL5222(): Unit = doTest()

  def testSCL5247(): Unit = doTest()

  def testSCL5250(): Unit = doTest()

  def testSCL5269(): Unit = doTest()

  def testSCL5303(): Unit = doTest()

  def testSCL5356(): Unit = doTest()

  def testSCL5337(): Unit = doTest()

  def testSCL5361(): Unit = doTest()

  def testSCL5393(): Unit = doTest()

  def testSCL5429(): Unit = doTest()

  def testSCL5454(): Unit = doTest()

  def testSCL5472(): Unit = doTest()

  def testSCL5472A(): Unit = doTest()

  def testSCL5472B(): Unit = doTest()

  def testSCL5472C(): Unit = doTest()

  def testSCL5475(): Unit = doTest()

  def testSCL5489(): Unit = doTest()

  def testSCL5538(): Unit = doTest()

  def testSCL5594(): Unit = doTest()

  def testSCL5650A(): Unit = doTest()

  def testSCL5650B(): Unit = doTest()

  def testSCL5650C(): Unit = doTest()

  def testSCL5661(): Unit = doTest()

  def testSCL5669(): Unit = doTest()

  def testSCL5669A(): Unit = doTest()

  def testSCL5669B(): Unit = doTest()

  def testSCL5681(): Unit = doTest()

  def testSCL5725(): Unit = doTest()

  def testSCL5729(): Unit = doTest()

  def testSCL5733(): Unit = doTest()

  def testSCL5736(): Unit = doTest()

  def testSCL5737(): Unit = doTest()

  def testSCL5738(): Unit = doTest()

  def testSCL5744(): Unit = doTest()

  def testSCL5834(): Unit = doTest()

  def testSCL5840(): Unit = doTest()

  def testSCL5856(): Unit = doTest()

  def testSCL5982(): Unit = doTest()

  def testSCL6022(): Unit = doTest()

  def testSCL6025(): Unit = doTest()

  def testSCL6079(): Unit = doTest()

  def testSCL6089(): Unit = doTest()

  def testSCL6091(): Unit = doTest()

  def testSCL6116(): Unit = doTest()

  def testSCL6118(): Unit = doTest()

  def testSCL6118B(): Unit = doTest()

  def testSCL6123(): Unit = doTest()

  def testSCL6157(): Unit = doTest()

  def testSCL6158(): Unit = doTest()

  def testSCL6169(): Unit = doTest()

  def testSCL6177(): Unit = doTest()

  def testSCL6195(): Unit = doTest()

  def testSCL6198(): Unit = doTest()

  def testSCL6235(): Unit = doTest()

  def testSCL6259(): Unit = doTest()

  def testSCL6270(): Unit = doTest()

  def testSCL6304(): Unit = doTest()

  def testSCL6309(): Unit = doTest()

  def testSCL6350(): Unit = doTest()

  def testSCL6386(): Unit = doTest()

  def testSCL6507(): Unit = doTest()

  def testSCL6511(): Unit = doTest()

  def testSCL6514(): Unit = doTest()

  def testSCL6541(): Unit = doTest()

  def testSCL6549(): Unit = doTest()

  def testSCL6601(): Unit = doTest()

  def testSCL6601B(): Unit = doTest()

  def testSCL6605A(): Unit = doTest()

  def testSCL6605B(): Unit = doTest()

  def testSCL6605C(): Unit = doTest()

  def testSCL6608(): Unit = doTest()

  def testSCL6608B(): Unit = doTest()

  def testSCL6658(): Unit = doTest()

  def testSCL6660(): Unit = doTest()

  def testSCL6667(): Unit = doTest()

  def testSCL6730(): Unit = doTest()

  def testSCL6730B(): Unit = doTest()

  def testSCL6745(): Unit = doTest()

  def testSCL6786(): Unit = doTest()

  def testSCL6787(): Unit = doTest()

  def testSCL6807(): Unit = doTest()

  def testSCL6854(): Unit = doTest()

  def testSCL6885(): Unit = doTest()

  def testSCL6978(): Unit = doTest()

  def testSCL7008(): Unit = doTest()

  def testSCL7031(): Unit = doTest()

  def testSCL7036(): Unit = doTest()

  def testSCL7043(): Unit = doTest()

  def testSCL7100(): Unit = doTest()

  def testSCL7174(): Unit = doTest()

  def testSCL7192(): Unit = doTest()

  def testSCL7268(): Unit = doTest()

  def testSCL7278(): Unit = doTest()

  def testSCL7321(): Unit = doTest()

  def testSCL7322(): Unit = doTest()

  def testSCL7388(): Unit = doTest()

  def testSCL7388B(): Unit = doTest()

  def testSCL7388C(): Unit = doTest()

  def testSCL7404(): Unit = doTest()

  def testSCL7413(): Unit = doTest()

  def testSCL7502A(): Unit = doTest()

  def testSCL7518(): Unit = doTest()

  def testSCL7502B(): Unit = doTest()

  def testSCL7544A(): Unit = doTest()

  def testSCL7544B(): Unit = doTest()

  def testSCL7604(): Unit = doTest()

  def testSCL7618(): Unit = doTest()

  def testSCL7805(): Unit = doTest()

  def testSCL7901(): Unit = doTest()

  def testSCL7927(): Unit = doTest()

  def testSCL8005(): Unit = doTest()

  def testSCL8005A(): Unit = doTest()

  def testSCL8036(): Unit = doTest()

  def testSCL8157A(): Unit = doTest()

  def testSCL8157B(): Unit = doTest()

  def testSCL8079(): Unit = doTest()

  def testSCL8178(): Unit = doTest()

  def testSCL8191(): Unit = doTest()

  def testSCL8232(): Unit = { doTest() }

  def testSCL8240(): Unit = doTest()

  def testSCL8241(): Unit = doTest()

  def testSCL8246(): Unit = doTest()

  def testSCL8261(): Unit = doTest()

  def testSCL8280(): Unit = doTest()

  def testSCL8282(): Unit = doTest()

  def testSCL8288(): Unit = doTest()

  def testSCL8317(): Unit = doTest()

  def testSCL8359(): Unit = doTest()

  def testSCL8398(): Unit = doTest()

  def testSCL8800(): Unit = doTest()

  def testSCL8933(): Unit = doTest()

  def testSCL8989(): Unit = doTest()

  def testSCL8995(): Unit = doTest()

  def testSCL9000(): Unit = doTest()

  def testSCL9426(): Unit = doTest()

  def testSCL9445(): Unit = {
    val extensionPoint = Extensions.getRootArea.getExtensionPoint(SyntheticMembersInjector.EP_NAME)
    val injector = new SCL9445Injector
    extensionPoint.registerExtension(injector)
    try {
      doTest()
    } finally {
      extensionPoint.unregisterExtension(injector)
    }
  }

  def testSCL9533(): Unit = {
    val extensionPoint = Extensions.getRootArea.getExtensionPoint(SyntheticMembersInjector.EP_NAME)
    val injector = new SCL9533Injector
    extensionPoint.registerExtension(injector)
    try {
      doTest()
    } finally {
      extensionPoint.unregisterExtension(injector)
    }
  }

  def testSCL9865(): Unit = {
    val extensionPoint = Extensions.getRootArea.getExtensionPoint(SyntheticMembersInjector.EP_NAME)
    val injector = new SCL9865Injector
    extensionPoint.registerExtension(injector)
    try {
      doTest()
    } finally {
      extensionPoint.unregisterExtension(injector)
    }
  }

  def testSOE(): Unit = doTest()

  def testSOE2(): Unit = doTest()

  def testSOEFix(): Unit = doTest()

  def testUnaryMethods(): Unit = doTest()
}