package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import model.mutable._
import reference._

object InformationAgent {
    import Information._
    
    def apply (info : Information, model : Model) : Unit = info match {
        case NewObject(name : String) => {
            if (model.objects contains name) throw MADException.ObjectNameTaken(name)
            if (name == "") throw MADException.ObjectNameEmpty
            model.objects += Object(name)
        }
        
        case IsConcept(name : String) => {
            model.objects(name).asConcept = Some(AsConcept())
        }
        
        case IsMachine(name : String, domain : ConceptRef, codomain : ConceptRef) => {
            model.objects(name).asMachine = Some(AsMachine(domain, codomain))
        }
        
        case MachineRelevant(concept, machine) => {
            model.objects(concept).asConcept.get.relatedMachines += machine
        }
        
        case Description(name : String, description : String) => {
            model.objects(name).description = description
        }
        
    }
}
