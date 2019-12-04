package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._
import reference._

abstract sealed class Structure {
    def toStructure : immutable.Structure
}

object Structure {
    case class Concept (relatedObjects : collection.mutable.Buffer[String] = collection.mutable.Buffer(), examples : collection.mutable.Buffer[String] = collection.mutable.Buffer()) extends Structure {
        def toStructure = immutable.Structure.Concept(relatedObjects.toSeq, examples.toSeq)
    }
    
    case class Machine (domain : ConceptRef, codomain : ConceptRef) extends Structure {
        def toStructure = immutable.Structure.Machine(domain, codomain)
    }
}
