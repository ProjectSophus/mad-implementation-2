package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import model.mutable._
import reference._

object InformationAgent {
    import Information._
    
    def apply (info : Information, model : Model) : Unit = info match {
        case NewConcept(uid : String, name : String) => {
            if (model.concepts contains uid) throw MADException.ConceptUIDTaken(uid)
            if (uid == "" | name == "") throw MADException.ConceptNameOrUIDEmpty
            model.concepts += uid -> Concept(name, None)
        }
        
        case NewMachine(uid : String, name : String, domain : ConceptRef, codomain : ConceptRef) => {
            if (model.machines contains uid) throw MADException.MachineUIDTaken(uid)
            if (uid == "" | name == "") throw MADException.MachineNameOrUIDEmpty
            model.machines += uid -> Machine(name, None, domain, codomain)
        }
        
        case ConceptDescription(uid : String, description : String) => {
            model.concepts(uid).description = Some(description)
        }
        
        case MachineDescription(uid : String, description : String) => {
            model.machines(uid).description = Some(description)
        }
    }
}
