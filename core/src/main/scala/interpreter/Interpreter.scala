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
        
        case NewMachineQuestion => {
            val name = data("Name").asInstanceOf[String]
            val domain = data("Domain").asInstanceOf[ConceptRef]
            val codomain = data("Codomain").asInstanceOf[ConceptRef]
            
            Seq(NewObject(name), IsMachine(name, domain, codomain))
        }
        
        case DescriptionQuestion(name) => {
            val description = data("Description").asInstanceOf[String]
            
            Seq(Description(name, description))
        }
        
        case ExampleQuestion(name) => {
            val obj = data("Object").asInstanceOf[String]
            
            val creation = if(model.objects.map(_.name) contains obj) Seq() else Seq(NewObject(obj))
            
            creation ++ Seq(IsExampleOf(name, obj))
        }
        
        case RepresentationQuestion(name) => {
            val rep = data("Object").asInstanceOf[String]
            
            val creation = if(model.objects.map(_.name) contains rep) Seq() else Seq(NewObject(rep))
            
            creation ++ Seq(IsRepresentation(rep), ObjectRelevant(name, rep))
        }
        
        case MachineRelatedQuestion(name) => {
            val mac = data("Machine").asInstanceOf[String]
            
            Seq(ObjectRelevant(name, mac))
        }
        
        case MachineQuestion(name, machinetype) => {
            val mname = data("Name").asInstanceOf[String]
            
            val (domain, codomain) = machinetype.signature.createConceptRefs(ConceptRef.BasicRef(name))
            
            val macname = f"$mname (on $name)"
            
            Seq(NewObject(macname), IsMachine(macname, domain, codomain), ObjectRelevant(name, macname))
        }
    }
}
