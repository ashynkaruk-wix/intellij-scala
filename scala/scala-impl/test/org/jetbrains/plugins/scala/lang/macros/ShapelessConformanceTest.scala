package org.jetbrains.plugins.scala.lang.macros

import org.jetbrains.plugins.scala.debugger.{ScalaVersion, Scala_2_12, Scala_2_13}

/**
  * Nikolay.Tropin
  * 29-Jan-18
  */
class ShapelessConformanceTest_2_12 extends ShapelessConformanceTest(Scala_2_12)
class ShapelessConformanceTest_2_13 extends ShapelessConformanceTest(Scala_2_13)

abstract class ShapelessConformanceTest(override val version: ScalaVersion)
  extends ShapelessConformanceTestBase()(version) {

  def testWitnessSelectDynamic(): Unit = doTest(
    s"""
       |object Test {
       |  type `"foo"` = shapeless.Witness.`"foo"`.T
       |}
       |val foo: Test.`"foo"` = "foo"
       |//True
     """.stripMargin
  )

  def testWitnessValSelectDynamic(): Unit = doTest(
    s"""
       |object Test {
       |  val W = shapeless.Witness
       |  type `"foo"` = W.`"foo"`.T
       |}
       |val foo: Test.`"foo"` = "foo"
       |//True
     """.stripMargin
  )

  def testWitnessSelectDynamicWrongLiteral(): Unit = doTest(
    s"""
       |object Test {
       |  type `"foo"` = shapeless.Witness.`"foo"`.T
       |}
       |val foo: Test.`"foo"` = "bar"
       |//False
     """.stripMargin
  )

  def testWitnessValSelectDynamicWrongLiteral(): Unit = doTest(
    s"""
       |object Test {
       |  val W = shapeless.Witness
       |  type `"foo"` = W.`"foo"`.T
       |}
       |val foo: Test.`"foo"` = "bar"
       |//False
     """.stripMargin
  )

  def testWitnessNegativeLiteral(): Unit = doTest(
    """
      |object Test {
      |  val W = shapeless.Witness
      |  type MinusOne = W.`-1`.T
      |}
      |val minusOne: Test.MinusOne = -1
      |//True
    """.stripMargin
  )

  def testWitnessInfixExpression(): Unit = doTest(
    """
      |object Test {
      |  val W = shapeless.Witness
      |  type Zero = W.`1 - 1`.T
      |}
      |val z: Test.Zero = 0
      |//True
    """.stripMargin
  )

  def testWitnessInfixExpressionWrong(): Unit = doTest(
    """
      |object Test {
      |  val W = shapeless.Witness
      |  type Zero = W.`1 - 1`.T
      |}
      |val z: Test.Zero = 1
      |//False
    """.stripMargin
  )
}