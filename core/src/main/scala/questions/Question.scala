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
            "Unique ID" -> Text,
            "Name" -> Text
        )
    )
    
    case object NewConceptQuestion extends Question (
        f"Please name a new concept?",
        Answer(
            "Unique ID" -> Text,
            "Name" -> Text
        )
    ) {
        override def hashCode() = 100
    }
}
