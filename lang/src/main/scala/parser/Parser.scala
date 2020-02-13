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
    def code : Parser[ScopedCode] = (NewLine.* ~> (command <~ NewLine.+).* ^^ (ScopedCode(_))).named("code")

    def command : Parser[Command] = (declaration | template | useTemplate | setVariable | module | using | javascript).named("command")
    
    def declaration : Parser[Declaration] = (concept | example | antiexample | representation | relevant | description | machinetypeon | machine | statement | generalization).named("declaration")
    
    def concept : Parser[IsConcept] = Concept ~> expressions ^^ {case ob => IsConcept(ob)}
    def example : Parser[IsExample] = Example ~> arguments ~ expressions ~ expressions ^^ {
        case arguments ~ ob1 ~ ob2 => {
            val bools = processArguments("AntiexampleForSpecialization")(arguments)
            IsExample(ob1, ob2, bools(0))
        }
    }
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
    
    def statement : Parser[IsStatement] = Statement ~> expression ~ expression ^^ {case ob ~ str => IsStatement(ob, str)}
    
    def generalization : Parser[IsGeneralization] = Generalization ~> expressions ~ expressions ^^ {case gen ~ spec => IsGeneralization(gen, spec)}
    
    def template : Parser[CreateTemplate] = (Template ~> stringOrIdentifier ~ templateParamsDec ~ (Extends ~> extensions).?) ~ (GroupOpen ~> code <~ GroupClose) ^^ {
        case name ~ params ~ extensions ~ code => CreateTemplate(name, params, extensions.getOrElse(Seq()), code)
    }
    
    def useTemplate : Parser[AST.UseTemplate] = Token.UseTemplate ~> expressions ~ templateParamsGroup ^^ {case exp ~ params => AST.UseTemplate(exp, params)}
    
    def setVariable : Parser[SetVariable] = stringOrIdentifier ~ (Equals ~> expression) ^^ {case varname ~ exp => SetVariable(varname, exp)}
    
    def module : Parser[AST.Module] = Token.Module ~> stringOrIdentifier ~ (GroupOpen ~> code <~ GroupClose) ^^ {
        case name ~ ast => AST.Module(name, ast)
    }
    
    def using : Parser[AST.Using] = Token.Using ~> stringOrIdentifier ^^ (AST.Using(_))
    
    def javascript : Parser[UseJavascript] = Javascript ~> stringLiteral ^^ (UseJavascript(_))
    
    def machinetype : Parser[MachineType] = acceptMatch("machinetype", {
        case x : MachineTypeKeyword => x.machinetype
    })
    
    
    def templateParamsGroup : Parser[Seq[Seq[Expression]]] = (templateParams.map(Seq(_)) | GroupOpen ~> repsep(templateParams, Separator) <~ GroupClose).named("group of template params")
    
    def templateParams : Parser[Seq[Expression]] = (ParamOpen ~> repsep(expression, Separator) <~ ParamClose).named("template params")
    
    def templateParamsDec : Parser[Seq[String]] = (ParamOpen ~> rep1sep(stringOrIdentifier, Separator) <~ ParamClose).named("template params declaration")
    
    def extensions : Parser[Seq[(String, Seq[Expression])]] = extension.map(Seq(_)) | (GroupOpen ~> repsep(extension, Separator) <~ GroupClose)
    
    def extension : Parser[(String, Seq[Expression])] = stringOrIdentifier ~ (ParamOpen ~> repsep(expression, Separator) <~ ParamClose) ^^ {
        case str ~ exprs => (str, exprs)
    }
    
    def expression : Parser[Expression] = (variableExpression | concreteExpression | interpolationExpression).named("expression")
    
    def concreteExpression : Parser[ConcreteExpression] = stringOrIdentifier.map(ConcreteExpression(_)).named("concrete expression")
    
    def variableExpression : Parser[VariableExpression] = (VarOpen ~> acceptMatch("variable name", {case Identifier(str) => VariableExpression(str)}) <~ VarClose).named("variable expression")
    
    def interpolationExpression : Parser[InterpolationExpression] = (Dollar ~> stringLiteral ^^ {case str => InterpolationExpression(str)}).named("interpolated expression")
    
    
    def expressions : Parser[Seq[Expression]] = ((expression.map(Seq(_))) | (GroupOpen ~> repsep(expression, Separator) <~ GroupClose)).named("expressions")
    
    
    def machineParam : Parser[Seq[Expression]] = ((expression.map(Seq(_))) | (TupleOpen ~> repsep(expression, Separator) <~ TupleClose)).named("machine parameter")
    
    def stringLiteral : Parser[String] = acceptMatch("string literal", {
        case StringLiteral(str) => str
    })
    
    def stringOrIdentifier : Parser[String] = acceptMatch("string or identifier", {
        case Identifier(str) => str
        case StringLiteral(str) => str
    })
    
    def arguments : Parser[Seq[String]] = (acceptMatch("argument", {case Argument(arg) => arg}).*).named("arguments")
    
    def processArguments(validArguments : String*)(args : Seq[String]) : Seq[Boolean] = {
        
        val arguments = collection.mutable.HashSet[String](args : _*)
        
        val bools = for {validArg <- validArguments} yield {
            if (arguments.contains(validArg)) {
                arguments.remove(validArg)
                true
            } else false
        }
        
        if (!arguments.isEmpty) {
            throw CompilerException(s"Too many arguments! The following arguments were not understood: ${arguments.mkString("(", ", ", ")")}")
        }
        
        bools
    }
    
}
