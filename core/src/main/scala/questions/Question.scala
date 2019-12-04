package io.github.ProjectSophus.mad.questions

import io.github.ProjectSophus.mad.model.immutable._
import io.github.ProjectSophus.mad.machinetype._

abstract sealed class Question(override val toString : String, val answer : Answer) {
    override def hashCode() = toString.hashCode()
}

object Question {
    def questions(model : Model) : Seq[Question] = Seq(
        Seq(NewConceptQuestion),
        
        for {
            name <- model.objects.map(_.name)
        } yield DescriptionQuestion (name),
        
        for {
            name <- model.concepts.map(_.name)
        } yield ExampleQuestion (name),
        
        for {
            name <- model.concepts.map(_.name)
        } yield RepresentationQuestion (name),
        
        for {
            name <- model.concepts.map(_.name)
        } yield MachineRelatedQuestion (name),
        
        for {
            name <- model.concepts.map(_.name)
            machinetype <- MachineType.machineTypes
        } yield MachineQuestion (name, machinetype),
        
    ).flatten
    
    import Answer._
    
    case object NewConceptQuestion extends Question (
        f"What concepts are there?",
        Answer("Please enter in plural" -> Label, "Name" -> Text)
    )
    
    case object NewMachineQuestion extends Question (
        f"What machines are there?",
        Answer("Name" -> Text, "Domain" -> ConceptRef, "Codomain" -> ConceptRef)
    )
    
    case class DescriptionQuestion (name : String) extends Question (
        f"What is the description of $name?",
        Answer("Description" -> LongText)
    )
    
    case class ExampleQuestion (name : String) extends Question (
        f"What are examples of $name?",
        Answer("Object" -> Object)
    )
    
    case class RepresentationQuestion (name : String) extends Question (
        f"What are ways of representing $name?",
        Answer("Object" -> Object)
    )
    
    case class MachineRelatedQuestion (name : String) extends Question (
        f"What machines are related to $name?",
        Answer("Machine" -> Machine)
    )
    
    case class MachineQuestion (name : String, machinetype : MachineType) extends Question (
        f"Which ${machinetype.plural.toLowerCase} does $name have?",
        Answer("Name" -> Text)
    )
}
