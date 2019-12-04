package io.github.ProjectSophus.mad.interpreter

import io.github.ProjectSophus.mad._
import questions._
import information._
import reference._
import model.immutable._

import Question._
import Information._

object Interpreter {
    def apply(q : Question, data : Map[String, Any], model : Model) : Seq[Information] = q match {
        case NewConceptQuestion => {
            val name = data("Name").asInstanceOf[String]
            
            Seq(NewObject(name), IsConcept(name))
        }
        
        case MachineQuestion(name, machinetype) => {
            val mname = data("Name").asInstanceOf[String]
            
            val (domain, codomain) = machinetype.signature.createConceptRefs(ConceptRef.BasicRef(name))
            
            val macname = f"$mname (on $name)"
            
            Seq(NewObject(macname), IsMachine(macname, domain, codomain), ObjectRelevant(name, macname))
        }
        
        case DescriptionQuestion(name) => {
            val description = data("Description").asInstanceOf[String]
            
            Seq(Description(name, description))
        }
    }
}
