package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model._
import information._
import questions._
import inference._

import collection.mutable.Buffer

class BasicMemory extends Memory {
    var model : mutable.Model = mutable.Model()
    val answers : Buffer[(Question, Buffer[Information])] = Buffer()
    var inferenceEngine : InferenceEngine = new InferenceEngine(this)
    
    def applyInformationSeq(question : Question, infoseq : Seq[Information]) = {
        def addInfo(info : Information) = InformationAgent(info, this, info => {
            applyInformation(question, info)
        })
        
        val buffer = Buffer[Information]()
        
        answers += question -> buffer
        
        infoseq foreach { info =>
            addInfo(info)
            buffer += info
        }
    }
    
    def getModel = model.toModel
    def getMutableModel = model
    def getAnswersTo(question : Question) = answers.collect{case (`question`, info) => info.toSeq}.toSeq
    
    def getAllInformation : Seq[Information] = for {
        (question, infoseq) <- answers
        info <- infoseq
    } yield info
    
    def clear() : Unit = {
        answers.clear()
        model = mutable.Model()
        inferenceEngine = new InferenceEngine(this)
    }
    
}
