package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang.parser.lexer._

import scala.util.parsing.input._

class TokenReader (tokens : Seq[Token], override val offset : Int = 0) extends Reader[Token] {
    def atEnd: Boolean = offset >= tokens.length
    def first: Token = if (offset < tokens.length) tokens(offset) else Token.EndToken
    def pos: Position = if (offset < tokens.length) tokens(offset).pos else NoPosition
    def rest: TokenReader = if (offset < tokens.length) new TokenReader(tokens, offset + 1) else this
}
