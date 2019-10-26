package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model.immutable._
import information._
import questions._

trait Memory {
    def applyInformation(quesition : Question, info : Information) : Unit
    
    def getModel : Model
    def getAnswersTo(question : Question) : Seq[Information]
}

object Memory {
    def apply() : Memory = new BasicMemory
}
