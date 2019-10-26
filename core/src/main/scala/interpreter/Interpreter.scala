package io.github.ProjectSophus.mad.interpreter

import io.github.ProjectSophus.mad._
import questions._
import information._
import reference._

import Question._
import Information._

object Interpreter {
    def apply(q : Question, data : Map[String, Any]) : Information = q match {
        case NewConceptQuestion => {
            val uid = data("Unique ID").asInstanceOf[String]
            val name = data("Name").asInstanceOf[String]
            
            NewConcept(uid, name)
        }
        
        case MachineQuestion(uid, _, machinetype) => {
            val muid = data("Unique ID").asInstanceOf[String]
            val mname = data("Name").asInstanceOf[String]
            
            val (domain, codomain) = machinetype.signature.createConceptRefs(ConceptRef.BasicRef(uid))
            
            NewMachine(muid, mname, domain, codomain)
        }
        
        case ConceptDescriptionQuestion(uid, _) => {
            val description = data("Description").asInstanceOf[String]
            
            ConceptDescription(uid, description)
        }
        
        case MachineDescriptionQuestion(uid, _) => {
            val description = data("Description").asInstanceOf[String]
            
            MachineDescription(uid, description)
        }
    }
}
