package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model._
import information._
import inference._
import questions._

trait Memory {
    def applyInformation(question : Question, info : Information) : Unit = applyInformationSeq(question, Seq(info))
    def applyInformationSeq(quesition : Question, info : Seq[Information]) : Unit
    
    def getModel : immutable.Model
    def getMutableModel : mutable.Model
    def getAnswersTo(question : Question) : Seq[Seq[Information]]
    
    def getAllInformation : Seq[Information]
    
    def clear() : Unit
    
    def inferenceEngine : InferenceEngine
}

object Memory {
    def apply() : Memory = new BasicMemory
}
