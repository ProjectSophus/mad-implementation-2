package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import model.mutable._
import reference._

object InformationAgent {
    import Information._
    
    def apply (info : Information, model : Model) : Unit = info match {
        case NewConcept(name : String) => {
            if (model.concepts contains name) throw MADException.ConceptNameTaken(name)
            if (name == "") throw MADException.ConceptNameEmpty
            model.concepts += name -> Concept(name, None, collection.mutable.Buffer())
        }
        
        case NewMachine(name : String, domain : ConceptRef, codomain : ConceptRef) => {
            if (model.machines contains name) throw MADException.MachineNameTaken(name)
            if (name == "") throw MADException.MachineNameEmpty
            model.machines += name -> Machine(name, None, domain, codomain)
        }
        
        case MachineRelevant(concept, machine) => {
            model.concepts(concept).relatedMachines += machine
        }
        
        case ConceptDescription(name : String, description : String) => {
            model.concepts(name).description = Some(description)
        }
        
        case MachineDescription(name : String, description : String) => {
            model.machines(name).description = Some(description)
        }
    }
}
