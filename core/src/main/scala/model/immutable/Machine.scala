package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad.reference._

case class Machine (name : String, description : Option[String], domain : ConceptRef, codomain : ConceptRef)
