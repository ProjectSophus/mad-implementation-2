package io.github.ProjectSophus.mad.memory

import io.github.ProjectSophus.mad._
import model.immutable._
import information._

trait Memory {
    def applyInformation(info : Information) : Unit
    
    def getModel : Model
}

object Memory {
    def apply() : Memory = new BasicMemory
}
