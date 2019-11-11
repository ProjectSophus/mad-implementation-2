package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._

case class AsConcept (relatedMachines : collection.mutable.Buffer[String] = collection.mutable.Buffer()) {
    def toAsConcept : immutable.AsConcept = immutable.AsConcept(relatedMachines.toSeq)
}
