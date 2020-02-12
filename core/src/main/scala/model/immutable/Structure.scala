package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Structure

object Structure {
    case class Concept (
        examples : Seq[String],
        antiexamples : Seq[String],
        generalizations : Seq[String],
        specializations : Seq[String]
    ) extends Structure
    
    case class Machine (domain : ConceptRef, codomain : ConceptRef) extends Structure
    case class Representation () extends Structure
    case class Statement (str : String) extends Structure
}
