package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._

import collection._

case class Model (
    concepts : mutable.Map[String, Concept],
    machines : mutable.Map[String, Machine]
) {
    def toModel : model.immutable.Model = model.immutable.Model(
        concepts.toMap.map{case (uid, concept) => uid -> concept.toConcept},
        machines.toMap.map{case (uid, machine) => uid -> machine.toMachine}
    )
}

object Model {
    def apply() : Model = Model(mutable.Map(), mutable.Map())
}
