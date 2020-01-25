package io.github.ProjectSophus.mad.lang.test

import io.github.ProjectSophus.mad.lang.parser._

import org.scalatest.FunSuite

class FileParserTest extends FunSuite {
    import AST._
    import scala.io.Source
    
    test ("Parsing example1.mad") {
        val source = Source.fromURL(getClass.getResource("/example1.mad"))
        val file = try source.mkString finally source.close
        Parser.getAST(file)
    }
    
}
