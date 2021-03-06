package org.ucombinator.lambdajs.simple

import org.ucombinator.util.CFAOptions
import org.ucombinator.lambdajs.cfa.pdcfa.LambdaJSPDCFARunner
import org.ucombinator.lambdajs.syntax.LJSyntax
import org.ucombinator.lambdajs.parsing.LambdaJSParser

/**
 * @author ilya
 */

object TestPDCFAForLambdaJS {

  import LJSyntax._

  val args_1_PDCFA_GC = Array("--pdcfa", "--k", "1", "--interrupt-after", "10000", "--dump-statistics", "--dump-graph", "--verbose", "id-example")

  // Examples
  val app = Fun(List(Var("f", 1), Var("x", 2)), App(Var("f", 1), List(Var("x", 2)), 3), 0)
  val id = Fun(List(Var("y", 4)), Var("y", 4), 5)

  val example1 = Let(Var("z", 6), EFloat(42.0), App(id, List(Var("z", 6)), 8), 7)
  val example2 = Let(Var("z", 6), EFloat(42.0), App(app, List(id, Var("z", 6)), 8), 7)

  val text3 = """
  (let ((g (lambda (f x) (f x)))
        (z (lambda (y) y)))
        (g z 42.0))
  """

  val text4 = """
  (let ((z 42.0))
       ((lambda (f x) (f x)) (lambda (y) y) z))
  """


  def main(args: Array[String]) {
    val opts = CFAOptions.parse(args_1_PDCFA_GC)
    val runner = new LambdaJSPDCFARunner(opts)


    val example3 = parseExample(text3)
    runner.runPDCFA(example3)
  }

  def parseExample(text: String) = {
    val parser = new LambdaJSParser
    val result = parser.parseText(text)
    result
  }

}
