package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Information (override val toString : String)

object Information {
    case class NewObject(name : String) extends Information(f"There is an object named $name")
    case class IsConcept(name : String) extends Information(f"The object $name is a concept")
    case class IsMachine(name : String, domain : ConceptRef, codomain : ConceptRef) extends Information(f"The object $name is a machine that goes from $domain to $codomain")
    
    case class IsRepresentation(name : String) extends Information(f"The object $name is a representation")
    
    case class IsExampleOf(concept : String, example : String) extends Information(f"The object $example is an example of $concept")
    case class IsAntiexampleOf(concept : String, antiexample : String) extends Information(f"The object $antiexample is an anti-example of $concept")
    
    case class ObjectRelevant(concept : String, ob : String) extends Information(f"The object $ob is relevant to $concept")
    
    case class Description(name : String, description : String) extends Information(f"""The object $name has description "$description"""")
}
