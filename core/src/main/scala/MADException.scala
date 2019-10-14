package io.github.ProjectSophus.mad

abstract sealed class MADException(msg : String) extends Exception(msg)

object MADException {
    case class ConceptUIDTaken(uid : String) extends MADException(f"The uid $uid is taken!")
    case object ConceptNameOrUIDEmpty extends MADException(f"The uid or name is empty!")
    
    case class MachineUIDTaken(uid : String) extends MADException(f"The uid $uid is taken!")
    case object MachineNameOrUIDEmpty extends MADException(f"The uid or name is empty!")
}
