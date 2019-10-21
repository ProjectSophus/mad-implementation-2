package io.github.ProjectSophus.mad.model

import io.github.ProjectSophus.mad.reference._

case class Machine (name : String, var description : Option[String], domain : ConceptRef, codomain : ConceptRef)
