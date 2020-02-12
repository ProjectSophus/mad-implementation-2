package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._
import reference._

abstract sealed class Structure {
    def toStructure : immutable.Structure
}

object Structure {
    case class Concept (
        examples : collection.mutable.Buffer[String] = collection.mutable.Buffer(),
        antiexamples : collection.mutable.Buffer[String] = collection.mutable.Buffer(),
        generalizations : collection.mutable.Buffer[String] = collection.mutable.Buffer(),
        specializations : collection.mutable.Buffer[String] = collection.mutable.Buffer()
    ) extends Structure {
        def toStructure = immutable.Structure.Concept(
            examples.toSeq,
            antiexamples.toSeq,
            generalizations.toSeq,
            specializations.toSeq
        )
    }
    
    case class Machine (domain : ConceptRef, codomain : ConceptRef) extends Structure {
        def toStructure = immutable.Structure.Machine(domain, codomain)
    }
    
    case class Representation () extends Structure {
        def toStructure = immutable.Structure.Representation()
    }
    
    case class Statement (str : String) extends Structure {
        def toStructure = immutable.Structure.Statement(str)
    }
}
