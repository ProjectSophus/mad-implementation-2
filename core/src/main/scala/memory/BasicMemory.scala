package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model._
import information._

class BasicMemory extends Memory {
    val model : mutable.Model = mutable.Model()
    
    def applyInformation(info : Information) = InformationAgent(info, model)
    
    def getModel = model.toModel
}
