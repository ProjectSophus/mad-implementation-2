package io.github.ProjectSophus.mad.questions

import Answer._

case class Answer (elements : (String, InputType)*)

object Answer {
    abstract sealed class InputType
    
    case object string extends InputType
}
