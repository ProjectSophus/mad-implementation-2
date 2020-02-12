package io.github.ProjectSophus.mad.lang.parser.lexer

import scala.util.parsing.combinator._


object Lexer extends RegexParsers {
    import Token._
    
    override val whiteSpace = """[ \t]+""".r
    
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
    
    def identifier : Parser[Token] = """[A-Za-z0-9\-_\.]+""".r ^^ (new Identifier(_))
    
    def stringLiteral : Parser[Token] = """"((?:\\"|(?:(?!").))*)"""".r ^^ {case t => StringLiteral(t.drop(1).dropRight(1))}
    
    def argument : Parser[Token] = """\~[A-Za-z]+""".r ^^ (str => Argument(str.drop(1)))
    
    def singlelinecomment : Parser[Token] = """\/\/.*\r?\n""".r ^^ (Comment(_))
    def multilinecomment : Parser[Token] = """\/\*((?!\*\/).|\r?\n)*\*\/""".r ^^ (Comment(_))
    
    def newline : Parser[Token] = "\n" ^^^ NewLine
    
    def total : Parser[Seq[Token]] = positioned(newline | multilinecomment | singlelinecomment | fixedToken | stringLiteral | argument | identifier).*
}
