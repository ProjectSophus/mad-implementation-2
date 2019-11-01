package io.github.ProjectSophus.mad

abstract sealed class MADException(msg : String) extends Exception(msg)

object MADException {
    case class ConceptNameTaken(name : String) extends MADException(f"The name $name is taken!")
    case object ConceptNameEmpty extends MADException(f"The concept name is empty!")
    
    case class MachineNameTaken(name : String) extends MADException(f"The name $name is taken!")
    case object MachineNameEmpty extends MADException(f"The machine name is empty!")
}
