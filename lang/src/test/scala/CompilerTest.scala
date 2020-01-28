package io.github.ProjectSophus.mad.lang.test

import io.github.ProjectSophus.mad.lang._
import parser._

import org.scalatest.FunSuite

class CompilerTest extends FunSuite {
    import AST._
    import scala.io.Source
    
    def compile(str : String) : Unit = {
        val source = Source.fromURL(getClass.getResource(str))
        val file = try source.mkString finally source.close
        val ast = Parser.getAST(file)
        
        val info = Compiler.compileASTtoInformation(ast)
        
        info foreach println
    }
    
    test ("Compiling exBasic.mad") {
        compile("/exBasic.mad")
    }
    
    test ("Compiling exRules.mad") {
        compile("/exRules.mad")
    }
    
}
