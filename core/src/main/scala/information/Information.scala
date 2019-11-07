package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import reference._

abstract sealed class Information (override val toString : String)

object Information {
    case class NewConcept(name : String) extends Information(f"There is a concept named $name")
    case class NewMachine(name : String, domain : ConceptRef, codomain : ConceptRef) extends Information(f"There is a machine named $name that goes from $domain to $codomain")
    
    case class MachineRelevant(concept : String, machine : String) extends Information(f"The machine $machine is relevant to $concept")
    
    case class ConceptDescription(name : String, description : String) extends Information(f"""The concept $name has description "$description"""")
    case class MachineDescription(name : String, description : String) extends Information(f"""The machine $name has description "$description"""")
}
