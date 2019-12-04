package io.github.ProjectSophus.mad.questions

import io.github.ProjectSophus.mad._
import reference._

import Answer._

case class Answer (elements : (String, InputType[_])*)

object Answer {
    abstract sealed class InputType[T]
    
    case object Label extends InputType[Unit]
    
    case object Text extends InputType[String]
    case object LongText extends InputType[String]
    
    case object ConceptRef extends InputType[ConceptRef]
    case object Object extends InputType[String]
    case object Machine extends InputType[String]
}
