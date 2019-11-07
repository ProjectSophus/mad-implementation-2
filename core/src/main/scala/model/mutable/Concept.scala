package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import reference._
import model._

case class Concept (name : String, var description : Option[String], relatedMachines : collection.mutable.Buffer[MachineRef]) {
    def toConcept : immutable.Concept = immutable.Concept(name, description, relatedMachines.toSeq)
}
