package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import reference._
import model._

case class AsMachine (domain : ConceptRef, codomain : ConceptRef) {
    def toAsMachine : immutable.AsMachine = immutable.AsMachine(domain, codomain)
}
