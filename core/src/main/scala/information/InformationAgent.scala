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
            model.obj(name).structure = Some(Some(Structure.Concept()))
        }
        
        case IsMachine(name : String, domain : ConceptRef, codomain : ConceptRef) => {
            model.obj(name).structure = Some(Some(Structure.Machine(domain, codomain)))
        }
        
        case IsRepresentation(name) => {
            model.obj(name).structure = Some(Some(Structure.Representation()))
        }
        
        case IsExampleOf(concept, example) => {
            if (!model.obj(concept).hasStructure[Structure.Concept]) throw MADException.RefNotAConcept(concept)
            model.obj(concept).getStructure[Structure.Concept].examples += example
            ()
        }
        
        case ObjectRelevant(concept, ob) => {
            if (!model.obj(concept).hasStructure[Structure.Concept]) throw MADException.RefNotAConcept(concept)
            model.obj(concept).getStructure[Structure.Concept].relatedObjects += ob
            ()
        }
        
        case Description(name : String, description : String) => {
            model.obj(name).description = Some(description)
        }
        
    }
}
