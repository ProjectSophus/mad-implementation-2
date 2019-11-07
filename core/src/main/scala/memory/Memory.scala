package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model.immutable._
import information._
import questions._

trait Memory {
    def applyInformation(question : Question, info : Information) : Unit = applyInformationSeq(question, Seq(info))
    def applyInformationSeq(quesition : Question, info : Seq[Information]) : Unit
    
    def getModel : Model
    def getAnswersTo(question : Question) : Seq[Seq[Information]]
}

object Memory {
    def apply() : Memory = new BasicMemory
}
