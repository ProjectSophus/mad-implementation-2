package io.github.ProjectSophus.mad.questions

import io.github.ProjectSophus.mad.model.immutable._
import io.github.ProjectSophus.mad.machinetype._

abstract sealed class Question(override val toString : String, val answer : Answer)

object Question {
    def questions(model : Model) : Seq[Question] = Seq(
        Seq(NewConceptQuestion),
        for {
            uid <- model.concepts.keys.toSeq
            machinetype <- MachineType.machineTypes
        } yield MachineQuestion (uid, model.concepts(uid).name, machinetype),
        for {
            uid <- model.concepts.keys.toSeq
        } yield ConceptDescriptionQuestion (uid, model.concepts(uid).name),
        for {
            uid <- model.machines.keys.toSeq
        } yield MachineDescriptionQuestion (uid, model.machines(uid).name)
    ).flatten
    
    import Answer._
    
    case class MachineQuestion (uid : String, name : String, machinetype : MachineType) extends Question (
        f"Which machines of type $machinetype does $name have?",
        Answer(
            "Unique ID" -> Text,
            "Name" -> Text
        )
    ) { override def hashCode() = (uid, name, machinetype).hashCode() }
    
    case object NewConceptQuestion extends Question (
        f"What concepts are there?",
        Answer(
            "Unique ID" -> Text,
            "Name" -> Text
        )
    ) { override def hashCode() = 0 }
    
    case class ConceptDescriptionQuestion (uid : String, name : String) extends Question (
        f"What is the description of $name?",
        Answer(
            "Description" -> Text
        )
    ) { override def hashCode() = (uid, name).hashCode() }
    
    case class MachineDescriptionQuestion (uid : String, name : String) extends Question (
        f"What is the description of $name?",
        Answer(
            "Description" -> Text
        )
    ) { override def hashCode() = (uid, name).hashCode() + 1 }
}
