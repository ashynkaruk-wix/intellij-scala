package org.jetbrains.plugins.scala
package base

import com.intellij.application.options.CodeStyle
import com.intellij.codeInsight.folding.CodeFoldingManager
import com.intellij.openapi.util.registry.Registry
import com.intellij.openapi.vfs.{VfsUtil, VirtualFile}
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.testFramework.{EditorTestUtil, LightPlatformTestCase, fixtures}

/**
 * User: Dmitry Naydanov
 * Date: 3/5/12
 */

abstract class ScalaLightCodeInsightFixtureTestAdapter
  extends fixtures.LightCodeInsightFixtureTestCase
    with debugger.DefaultScalaSdkOwner
    with FailableTest {

  import ScalaLightCodeInsightFixtureTestAdapter._
  import libraryLoaders._

  override final def getFixture: fixtures.JavaCodeInsightTestFixture = myFixture

  override def getTestDataPath: String = util.TestUtils.getTestDataPath + "/"

  protected def loadScalaLibrary: Boolean = true

  override def librariesLoaders: Seq[LibraryLoader] = Seq(
    ScalaSDKLoader(),
    HeavyJDKLoader()
  )

  override protected def getProjectDescriptor =
    DelegatingProjectDescriptor.withAfterSetupProject(super.getProjectDescriptor) { () =>
      afterSetUpProject()
    }

  protected def afterSetUpProject(): Unit = {
    Registry.get("ast.loading.filter").setValue(true, getTestRootDisposable)
    if (loadScalaLibrary) {
      getFixture.allowTreeAccessForAllFiles()
      setUpLibraries()
    }
  }

  override def tearDown(): Unit = {
    disposeLibraries()
    super.tearDown()
  }

  protected def configureFromFileText(fileText: String): PsiFile =
    getFixture.configureByText(
      ScalaFileType.INSTANCE,
      normalize(fileText)
    )

  protected def checkTextHasNoErrors(text: String): Unit = {
    getFixture.configureByText(
      ScalaFileType.INSTANCE,
      text
    )
    CodeFoldingManager.getInstance(getProject).buildInitialFoldings(getEditor)

    if (shouldPass) {
      testHighlighting(getFile.getVirtualFile)
    } else {
      try {
        testHighlighting(getFile.getVirtualFile)
      } catch {
        case _: AssertionError => return
      }
      failingTestPassed()
    }
  }

  protected def failingTestPassed(): Unit = throw new RuntimeException(failingPassed)

  protected def getCurrentCodeStyleSettings: CodeStyleSettings = CodeStyle.getSettings(getProject)

  protected def getCommonSettings = getCurrentCodeStyleSettings.getCommonSettings(ScalaLanguage.INSTANCE)

  private def testHighlighting(virtualFile: VirtualFile): Unit = getFixture.testHighlighting(
    false,
    false,
    false,
    virtualFile
  )
}

object ScalaLightCodeInsightFixtureTestAdapter {

  def normalize(text: String, stripTrailingSpaces: Boolean = true): String =
    text.stripMargin.replace("\r", "") match {
      case result if stripTrailingSpaces => result.trim
      case result => result
    }

  def findCaretOffset(text: String, stripTrailingSpaces: Boolean): (String, Int) = {
    import EditorTestUtil.CARET_TAG

    val normalized = normalize(text, stripTrailingSpaces)
    (normalized.replace(CARET_TAG, ""), normalized.indexOf(CARET_TAG))
  }

  implicit class Ext(private val adapter: ScalaLightCodeInsightFixtureTestAdapter) extends AnyVal {

    def configureJavaFile(fileText: String,
                          className: String,
                          packageName: String = null): Unit = inWriteAction {
      val root = LightPlatformTestCase.getSourceRoot match {
        case sourceRoot if packageName == null => sourceRoot
        case sourceRoot => sourceRoot.createChildDirectory(null, packageName)
      }

      val file = root.createChildData(null, className + ".java")
      VfsUtil.saveText(file, normalize(fileText))
    }
  }
}
