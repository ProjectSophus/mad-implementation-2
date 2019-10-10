package io.github.ProjectSophus.mad.questions

import Answer._

case class Answer (elements : (String, InputType[_])*)

object Answer {
    abstract sealed class InputType[T]
    
    case object string extends InputType[String]
}
