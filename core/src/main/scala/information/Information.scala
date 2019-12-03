package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Information (override val toString : String)

object Information {
    case class NewObject(name : String) extends Information(f"There is an object named $name")
    case class IsConcept(name : String) extends Information(f"The object $name is a concept")
    case class IsMachine(name : String, domain : ConceptRef, codomain : ConceptRef) extends Information(f"The object $name is a machine that goes from $domain to $codomain")
    
    case class ObjectRelevant(concept : String, ob : String) extends Information(f"The object $ob is relevant to $concept")
    
    case class Description(name : String, description : String) extends Information(f"""The object $name has description "$description"""")
}
