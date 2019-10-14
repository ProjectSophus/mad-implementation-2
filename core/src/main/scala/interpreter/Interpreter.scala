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
            val uid = data("Unique ID").asInstanceOf[String]
            val name = data("Name").asInstanceOf[String]
            
            Seq(NewConcept(uid, name))
        }
        
        case MachineQuestion(uid, _, machinetype) => {
            val muid = data("Unique ID").asInstanceOf[String]
            val mname = data("Name").asInstanceOf[String]
            
            val (domain, codomain) = machinetype.signature.createConceptRefs(ConceptRef.BasicRef(uid))
            
            Seq(NewMachine(muid, mname, domain, codomain))
        }
    }
}
