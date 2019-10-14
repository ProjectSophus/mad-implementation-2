package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Information (override val toString : String)

object Information {
    case class NewConcept(uid : String, name : String) extends Information(f"There is a concept named $name")
    case class NewMachine(uid : String, name : String, domain : ConceptRef, codomain : ConceptRef) extends Information(f"There is a machine named $name that goes from $domain to $codomain")
}
