package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang.parser.lexer._
import io.github.ProjectSophus.mad.lang._

import scala.util.parsing.combinator._

object Parser extends Parsers {
    import Token._
    import AST._
    
    type Elem = Token
    
    def getAST (data : String) : AST = getASTFromTokens(Lexer.getTokens(data))
    
    def getASTFromTokens (tokens : Seq[Token]) : AST = phrase(ast)(new TokenReader(tokens)) match {
        case Success(result, _) => result
        case Failure(msg, next) => throw ParserException(msg, next.pos)
        case Error(msg, next) => throw ParserException(msg, next.pos)
    }
    
    def ast : Parser[AST] = code
    def code : Parser[ScopedCode] = NewLine.* ~> (command <~ NewLine.+).* ^^ (ScopedCode(_))
    
    def command : Parser[Command] = declaration | template | useTemplate
    
    def declaration : Parser[Declaration] = concept | example | antiexample | representation | relevant | description | machinetypeon | machine | statement
    
    def concept : Parser[IsConcept] = Concept ~> expressions ^^ {case ob => IsConcept(ob)}
    def example : Parser[IsExample] = Example ~> expressions ~ expressions ^^ {case ob1 ~ ob2 => IsExample(ob1, ob2)}
    def antiexample : Parser[IsAntiexample] = Antiexample ~> expressions ~ expressions ^^ {case ob1 ~ ob2 => IsAntiexample(ob1, ob2)}
    def representation : Parser[IsRepresentation] = Representation ~> expressions ~ expressions ^^ {case ob1 ~ ob2 => IsRepresentation(ob1, ob2)}
    def relevant : Parser[IsRelevant] = Relevant ~> expressions ~ expressions ^^ {case ob1 ~ ob2 =>
    IsRelevant(ob1, ob2)}
    def description : Parser[HasDescription] = Description ~> expressions ~ stringLiteral ^^ {case ob ~ desc =>
    HasDescription(ob, desc)}
    
    def machinetypeon : Parser[IsMachineTypeOn] = machinetype ~ expressions ~ expressions ^^ {case mt ~ ob1 ~ ob2 => IsMachineTypeOn(mt, ob1, ob2)}
    
    def machine : Parser[IsMachine] = (Machine ~> expressions <~ DoubleColon) ~ (machineParam <~ Arrow) ~ machineParam ^^ {
        case ob ~ param1 ~ param2 => IsMachine(param1, param2, ob)
    }
    
    def statement : Parser[IsStatement] = Statement ~> expression ~ stringLiteral ^^ {case ob ~ str => IsStatement(ob, str)}
    
    def template : Parser[CreateTemplate] = (Template ~> stringOrIdentifier ~ templateParamsDec) ~ (GroupOpen ~> code <~ GroupClose) ^^ {
        case name ~ params ~ code => CreateTemplate(name, params, code)
    }
    
    def useTemplate : Parser[AST.UseTemplate] = Token.UseTemplate ~> expressions ~ templateParamsGroup ^^ {case exp ~ params => AST.UseTemplate(exp, params)}
        
    
    def machinetype : Parser[MachineType] = acceptMatch("machinetype", {
        case x : MachineTypeKeyword => x.machinetype
    })
    
    
    def templateParamsGroup : Parser[Seq[Seq[Expression]]] = templateParams.map(Seq(_)) | GroupOpen ~> repsep(templateParams, Separator) <~ GroupClose
    
    def templateParams : Parser[Seq[Expression]] = ParamOpen ~> repsep(expression, Separator) <~ ParamClose
    
    def templateParamsDec : Parser[Seq[String]] = ParamOpen ~> rep1sep(stringOrIdentifier, Separator) <~ ParamClose
    
    
    def expression : Parser[Expression] = variableExpression | concreteExpression | interpolationExpression
    
    def concreteExpression : Parser[ConcreteExpression] = stringOrIdentifier.map(ConcreteExpression(_))
    
    def variableExpression : Parser[VariableExpression] = VarOpen ~> acceptMatch("variable name", {case Identifier(str) => VariableExpression(str)}) <~ VarClose
    
    def interpolationExpression : Parser[InterpolationExpression] = Dollar ~> stringLiteral ^^ {case str => InterpolationExpression(str)}
    
    
    def expressions : Parser[Seq[Expression]] = (expression.map(Seq(_))) | (GroupOpen ~> repsep(expression, Separator) <~ GroupClose)
    
    
    def machineParam : Parser[Seq[Expression]] = (expression.map(Seq(_))) | (TupleOpen ~> repsep(expression, Separator) <~ TupleClose)
    
    def stringLiteral : Parser[String] = acceptMatch("string literal", {
        case StringLiteral(str) => str
    })
    
    def stringOrIdentifier : Parser[String] = acceptMatch("string or identifier", {
        case Identifier(str) => str
        case StringLiteral(str) => str
    })
    
    
}
