package io.github.ProjectSophus.mad.lang.test

import io.github.ProjectSophus.mad.lang._
import parser._

import org.scalatest.FunSuite

class CompilerTest extends FunSuite {
    import AST._
    import scala.io.Source
    
    test ("Compiling example1.mad") {
        val source = Source.fromURL(getClass.getResource("/example1.mad"))
        val file = try source.mkString finally source.close
        val ast = Parser.getAST(file)
        
        val info = Compiler.compileASTtoInformation(ast)
        
        info foreach println
    }
    
}
