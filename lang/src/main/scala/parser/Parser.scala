package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang.parser.lexer._
import io.github.ProjectSophus.mad.lang._

import scala.util.parsing.combinator._

object Parser extends Parsers {
    import Token._
    import AST._
    
    type Elem = Token
    
    def getAST (data : String) : AST = getASTFromTokens(Lexer.getTokens(data))
    
    def getASTFromTokens (tokens : Seq[Token]) : AST = phrase(module)(new TokenReader(tokens)) match {
        case Success(result, _) => result
        case Failure(msg, next) => throw ParserException(msg, next.pos)
        case Error(msg, next) => throw ParserException(msg, next.pos)
    }
    
    def module : Parser[AST] = (declaration <~ Semicolon).* ^^ (Module(_ : _*))
    
    def declaration : Parser[Declaration] = concept | example | antiexample | representation | machinetypeon | machine
    
    def concept : Parser[IsConcept] = Concept ~> objects ^^ {case ob => IsConcept(ob)}
    def example : Parser[IsExample] = Example ~> objects ~ objects ^^ {case ob1 ~ ob2 => IsExample(ob1, ob2)}
    def antiexample : Parser[IsAntiexample] = Antiexample ~> objects ~ objects ^^ {case ob1 ~ ob2 => IsAntiexample(ob1, ob2)}
    def representation : Parser[IsRepresentation] = Representation ~> objects ~ objects ^^ {case ob1 ~ ob2 => IsRepresentation(ob1, ob2)}
    def machinetypeon : Parser[IsMachineTypeOn] = machinetype ~ objects ~ objects ^^ {case mt ~ ob1 ~ ob2 => IsMachineTypeOn(mt, ob1, ob2)}
    
    def machine : Parser[IsMachine] = (Machine ~> objects <~ DoubleColon) ~ (objectParams <~ Arrow) ~ objectParams ^^ {
        case ob ~ param1 ~ param2 => IsMachine(param1, param2, ob)
    }
    
    
    def machinetype : Parser[MachineType] = acceptMatch("machinetype", {
        case x : MachineTypeKeyword => x.machinetype
    })
    
    
    def obj : Parser[Object] = acceptMatch("object", {
        case Identifier(str) => Object(str)
        case StringLiteral(str) => Object(str)
    })
    
    def objects : Parser[Seq[Object]] = (obj.map(Seq(_))) | (GroupOpen ~> repsep(obj, Separator) <~ GroupClose)
    
    def objectParams : Parser[Seq[Object]] = (obj.map(Seq(_))) | (TupleOpen ~> repsep(obj, Separator) <~ TupleClose)
    
}
