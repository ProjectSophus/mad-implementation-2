package io.github.ProjectSophus.mad

abstract sealed class MADException(msg : String) extends Exception(msg)

object MADException {
    case class ObjectNameTaken(name : String) extends MADException(f"The name $name is taken!")
    case object ObjectNameEmpty extends MADException(f"The object name is empty!")
    
    case class RefNotAConcept(concept : String) extends MADException(f"The object $concept is not a concept!")
}
