package io.github.torsteinvik.flow.parser.lexer

import scala.util.parsing.input._

abstract sealed class Token(str : String) extends Positional

object Token {
    
    case object EndToken extends Token("")
    
    abstract sealed class Keyword(keyword : String) extends Token(keyword)
    
    case object Concept extends Keyword("Concept")
    case object Example extends Keyword("Example")
    case object Antiexample extends Keyword("Antiexample")
    case object Representation extends Keyword("Representation of")
    case object Operation extends Keyword("Operation on")
    case object Function extends Keyword("Function on")
    case object Relation extends Keyword("Relation on")
    case object Property extends Keyword("Property on")
    
    val keywords : Seq[Keyword] = Seq(Concept, Example, Antiexample, Representation, Operation, Function, Relation, Property)
    
    abstract sealed class Punctuation(symbol : String) extends Token(symbol)
    
    case object DoubleColon extends Punctuation("::")
    case object Machine extends Punctuation("->")
    case object TupleOpen extends Punctuation("(")
    case object TupleClose extends Punctuation(")")
    case object GroupOpen extends Punctuation("{")
    case object GroupClose extends Punctuation("}")
    case object Separator extends Punctuation(",")
    
    val punctuation : Seq[Punctuation] = Seq(DoubleColon, Machine, TupleOpen, TupleClose, GroupOpen, GroupClose, Separator)
    
    case class Comment(content : String) extends Token("/* " + content + " */")
    
    case class Identifier (val name : String) extends Token(name)
    
    case class StringLiteral (val content : String) extends Token("\"" + content + "\"")*/
    
}
