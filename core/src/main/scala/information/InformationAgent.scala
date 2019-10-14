package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import io.github.ProjectSophus.mad.model._

object InformationAgent {
    import Information._
    
    def apply (info : Information, model : Model) : Model = info match {
        case NewConcept(uid : String, name : String) => {
            if (model.concepts contains uid) throw MADException.ConceptUIDTaken(uid)
            if (uid == "" | name == "") throw MADException.ConceptNameOrUIDEmpty
            model.copy(concepts = model.concepts + (uid -> Concept(name, None)))
        }
    }
}
