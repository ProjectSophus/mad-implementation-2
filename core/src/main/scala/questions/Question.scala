package io.github.ProjectSophus.mad.questions

import io.github.ProjectSophus.mad.model._
import io.github.ProjectSophus.mad.machinetype._

abstract sealed class Question(override val toString : String, val answer : Answer)

object Question {
    def questions(model : Model) : Seq[Question] = {
        (for {
            uid <- model.concepts.keys.toSeq
            machinetype <- MachineType.machineTypes
        } yield MachineQuestion (uid, model.concepts(uid).name, machinetype)) ++ Seq(NewConceptQuestion)
    }
    
    import Answer._
    
    case class MachineQuestion (uid : String, name : String, machinetype : MachineType) extends Question (
        f"Which machines of type $machinetype does $name have?",
        Answer(
            "uid" -> string,
            "name" -> string
        )
    )
    
    case object NewConceptQuestion extends Question (
        f"Please name a new concept?",
        Answer(
            "uid" -> string,
            "name" -> string
        )
    ) {
        override def hashCode() = 100
    }
}
