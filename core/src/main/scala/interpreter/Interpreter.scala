package io.github.ProjectSophus.mad.interpreter

import io.github.ProjectSophus.mad._
import questions._
import information._
import reference._

import Question._
import Information._

object Interpreter {
    def apply(q : Question, data : Map[String, Any]) : Seq[Information] = q match {
        case NewConceptQuestion => {
            val name = data("Name").asInstanceOf[String]
            
            Seq(NewConcept(name))
        }
        
        case MachineQuestion(name, machinetype) => {
            val mname = data("Name").asInstanceOf[String]
            
            val (domain, codomain) = machinetype.signature.createConceptRefs(ConceptRef.BasicRef(name))
            
            val macname = f"$mname (on $name)"
            
            Seq(NewMachine(macname, domain, codomain), MachineRelevant(name, macname))
        }
        
        case ConceptDescriptionQuestion(name) => {
            val description = data("Description").asInstanceOf[String]
            
            Seq(ConceptDescription(name, description))
        }
        
        case MachineDescriptionQuestion(name) => {
            val description = data("Description").asInstanceOf[String]
            
            Seq(MachineDescription(name, description))
        }
    }
}
