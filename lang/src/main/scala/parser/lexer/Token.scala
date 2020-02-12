package io.github.ProjectSophus.mad.lang.parser.lexer

import io.github.ProjectSophus.mad.lang._

import scala.util.parsing.input._

abstract sealed class Token(val str : String) extends Positional

object Token {
    
    case object EndToken extends Token("")
    case object NewLine extends Token("\n")
    
    abstract sealed class Keyword(keyword : String) extends Token(keyword)
    
    abstract sealed class MachineTypeKeyword(keyword : String, val machinetype : MachineType) extends Keyword(keyword)
    
    case object Concept extends Keyword("Concept")
    case object Example extends Keyword("Example")
    case object Antiexample extends Keyword("Antiexample")
    case object Representation extends Keyword("Representation of")
    case object Relevant extends Keyword("Relevant")
    case object Description extends Keyword("Description")
    case object Machine extends Keyword("Machine")
    case object Operation extends MachineTypeKeyword("Operation on", MachineType.Operation)
    case object Function extends MachineTypeKeyword("Function on", MachineType.Function)
    case object Relation extends MachineTypeKeyword("Relation on", MachineType.Relation)
    case object Property extends MachineTypeKeyword("Property on", MachineType.Property)
    case object Statement extends Keyword("Statement")
    case object Generalization extends Keyword("Generalization")
    case object Template extends Keyword("Template")
    case object UseTemplate extends Keyword("UseTemplate")
    case object Module extends Keyword("Module")
    case object Using extends Keyword("Using")
    
    val keywords : Seq[Keyword] = Seq(Concept, Example, Antiexample, Representation, Relevant, Description, Machine, Operation, Function, Relation, Property, Statement, Generalization, Template, UseTemplate, Module, Using)
    
    abstract sealed class Punctuation(symbol : String) extends Token(symbol)
    
    case object DoubleColon extends Punctuation("::")
    case object Arrow extends Punctuation("->")
    case object TupleOpen extends Punctuation("(")
    case object TupleClose extends Punctuation(")")
    case object GroupOpen extends Punctuation("{")
    case object GroupClose extends Punctuation("}")
    case object VarOpen extends Punctuation("<")
    case object VarClose extends Punctuation(">")
    case object ParamOpen extends Punctuation("[")
    case object ParamClose extends Punctuation("]")
    case object Separator extends Punctuation(",")
    case object Dollar extends Punctuation("$")
    case object Equals extends Punctuation("=")
    
    val punctuation : Seq[Punctuation] = Seq(DoubleColon, Arrow, TupleOpen, TupleClose, GroupOpen, GroupClose, VarOpen, VarClose, ParamOpen, ParamClose, Separator, Dollar, Equals)
    
    case class Comment(content : String) extends Token("/* " + content + " */")
    
    case class Argument(arg : String) extends Token(s"~$arg")
    
    case class Identifier (val name : String) extends Token(name)
    
    case class StringLiteral (val content : String) extends Token("\"" + content + "\"")
    
}
