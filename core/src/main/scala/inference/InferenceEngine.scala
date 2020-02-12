package io.github.ProjectSophus.mad.inference

import io.github.ProjectSophus.mad._
import memory._
import information._

import collection.mutable.Buffer

class InferenceEngine(memory : Memory) {
    
    val inferers : Buffer[Inferer] = Buffer[Inferer]()
    
    def addInferer(inferer : Inferer, backtrack : Boolean = true) = {
        inferers += inferer
        
        if (backtrack) {
            for {
                previnfo <- memory.getAllInformation
                newinfo <- inferer.infer(previnfo)
            } yield {
                memory.applyInformation(questions.Question.NullQuestion, newinfo)
            }
        }
    }
    
    def infer(info : Information) : Seq[Information] = for {
        inferer <- inferers
        newinfo <- inferer.infer(info)
    } yield newinfo
    
}
