package io.github.ProjectSophus.mad.questions

import io.github.ProjectSophus.mad.model.immutable._
import io.github.ProjectSophus.mad.machinetype._

abstract sealed class Question(override val toString : String, val answer : Answer)

object Question {
    def questions(model : Model) : Seq[Question] = Seq(
        Seq(NewConceptQuestion),
        for {
            name <- model.concepts.keys.toSeq
            machinetype <- MachineType.machineTypes
        } yield MachineQuestion (name, machinetype),
        for {
            name <- model.concepts.keys.toSeq
        } yield ConceptDescriptionQuestion (name),
        for {
            name <- model.machines.keys.toSeq
        } yield MachineDescriptionQuestion (name)
    ).flatten
    
    import Answer._
    
    case class MachineQuestion (name : String, machinetype : MachineType) extends Question (
        f"Which ${machinetype.plural.toLowerCase} does $name have?",
        Answer(
            "Name" -> Text
        )
    ) { override def hashCode() = (name, machinetype).hashCode() }
    
    case object NewConceptQuestion extends Question (
        f"What concepts are there?",
        Answer(
            "Name" -> Text
        )
    ) { override def hashCode() = 0 }
    
    case class ConceptDescriptionQuestion (name : String) extends Question (
        f"What is the description of $name?",
        Answer(
            "Description" -> LongText
        )
    ) { override def hashCode() = name.hashCode() }
    
    case class MachineDescriptionQuestion (name : String) extends Question (
        f"What is the description of $name?",
        Answer(
            "Description" -> LongText
        )
    ) { override def hashCode() = name.hashCode() + 1 }
}
