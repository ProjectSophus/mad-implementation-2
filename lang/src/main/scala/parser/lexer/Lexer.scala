package io.github.torsteinvik.flow.parser.lexer

import scala.util.parsing.combinator._
import scala.util.matching.Regex


object Lexer extends RegexParsers {
    import Token._
    
    @throws[LexerException]("if there is a lexing error or no parse")
    def getTokens(data : String) : Seq[Token] = parseAll(total, data) match {
        case Success(result, _) => result.filter{
            case Comment(_) => false
            case _ => true
        }
        
        case Failure(msg, next) => throw LexerException(msg, next.pos)
        case Error(msg, next) => throw LexerException(msg, next.pos)
    }
    
    val fixedTokens = keywords ++ punctuation
    
    def fixedToken : Parser[Token] = fixedTokens.map(token => token.str ^^^ token).reduceLeft(_ | _)
    
    def identifier : Parser[Token] = """[A-Za-z0-9]+""".r ^^ (new Identifier(_))
    
    def singlelinecomment : Parser[Token] = """\/\/.*\r?\n""".r ^^ (Comment(_))
    def multilinecomment : Parser[Token] = """\/\*((?!\*\/).|\r?\n)*\*\/""".r ^^ (Comment(_))
    
    def total : Parser[Seq[Token]] = positioned(multilinecomment | singlelinecomment | fixedToken | identifier).*
}
