package io.github.torsteinvik.flow.parser.lexer

import scala.util.parsing.input._


case class LexerException(msg : String, pos : Position) extends
    Exception("Lexical error at line " + pos.line + " column " + pos.column + "\n" + pos.longString)
