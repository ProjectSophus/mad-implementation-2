package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import reference._
import model._

case class Machine (name : String, var description : Option[String], domain : ConceptRef, codomain : ConceptRef) {
    def toMachine : immutable.Machine = immutable.Machine(name, description, domain, codomain)
}
