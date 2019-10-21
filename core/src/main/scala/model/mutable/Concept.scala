package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._

case class Concept (name : String, var description : Option[String]) {
    def toConcept : immutable.Concept = immutable.Concept(name, description)
}
