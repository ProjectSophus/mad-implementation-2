package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Structure

object Structure {
    case class Concept (relatedObjects : Seq[String], examples : Seq[String]) extends Structure
    case class Machine (domain : ConceptRef, codomain : ConceptRef) extends Structure
    case class Representation () extends Structure
}
