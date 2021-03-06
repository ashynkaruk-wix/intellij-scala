package org.jetbrains.plugins.scala
package worksheet.actions

import java.awt.BorderLayout

import javax.swing.{DefaultBoundedRangeModel, Icon}
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.openapi.actionSystem._
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.{PsiDocumentManager, PsiFile}
import org.jetbrains.plugins.scala.extensions._
import org.jetbrains.plugins.scala.lang.psi.api.ScalaFile
import worksheet.runconfiguration.WorksheetCache
import worksheet.ui.WorksheetEditorPrinterFactory

/**
 * @author Ksenia.Sautina
 * @author Dmitry Naydanov
 * @since 11/12/12
 */
class CleanWorksheetAction() extends AnAction with TopComponentAction {

  def actionPerformed(e: AnActionEvent) {
    val project = e.getProject
    if (project == null) return //EA-72055

    val editor: Editor = FileEditorManager.getInstance(project).getSelectedTextEditor
    val file: VirtualFile = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext)

    if (editor == null || file == null) return

    CleanWorksheetAction.cleanAll(editor, file, project)
  }

  override def actionIcon: Icon = AllIcons.Actions.GC

  override def bundleKey = "worksheet.clear.button"
}

object CleanWorksheetAction {
  def cleanAll(editor: Editor, file: VirtualFile, project: Project): Unit = {
    val psiFile: PsiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument)
    val viewer = WorksheetCache.getInstance(project).getViewer(editor)

    if (psiFile == null || viewer == null) return

    val splitPane = viewer.getComponent.getParent
    if (splitPane == null) return
    val parent = splitPane.getParent
    if (parent == null) return

    invokeLater {
      inWriteAction {
        CleanWorksheetAction.resetScrollModel(viewer)
        WorksheetCache.getInstance(project).removePrinter(editor)
        CleanWorksheetAction.cleanWorksheet(psiFile.getNode, editor, viewer, project)

        parent.remove(splitPane)
        parent.add(editor.getComponent, BorderLayout.CENTER)
        editor.getSettings.setFoldingOutlineShown(true)
      }
    }
  }

  def resetScrollModel(viewer: Editor) {
    viewer match {
      case viewerEx: EditorImpl =>
        val commonModel = viewerEx.getScrollPane.getVerticalScrollBar.getModel
        viewerEx.getScrollPane.getVerticalScrollBar.setModel(
          new DefaultBoundedRangeModel(
            commonModel.getValue, commonModel.getExtent, commonModel.getMinimum, commonModel.getMaximum
          )
        )
      case _ =>
    }
  }

  def cleanWorksheet(node: ASTNode, leftEditor: Editor, rightEditor: Editor, project: Project) {
    val rightDocument = rightEditor.getDocument

    WorksheetEditorPrinterFactory.deleteWorksheetEvaluation(node.getPsi.asInstanceOf[ScalaFile])

    if (rightDocument != null && !project.isDisposed) {
      ApplicationManager.getApplication runWriteAction new Runnable {
        override def run() {
          rightDocument.setText("")
          PsiDocumentManager.getInstance(project).commitDocument(rightDocument)
        }
      }
    }
  }
}