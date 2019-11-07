package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model._
import information._
import questions._

class BasicMemory extends Memory {
    val model : mutable.Model = mutable.Model()
    val answers : collection.mutable.Buffer[(Question, Seq[Information])] = collection.mutable.Buffer()
    
    def applyInformationSeq(question : Question, infoseq : Seq[Information]) = {
        infoseq foreach {info => InformationAgent(info, model)}
        answers += question -> infoseq
    }
    
    def getModel = model.toModel
    def getAnswersTo(question : Question) = answers.collect{case (`question`, info) => info}.toSeq
    
}
