package io.github.ProjectSophus.mad.reference

abstract sealed class ConceptRef(override val toString : String)

object ConceptRef {
    case class BasicRef (name : String) extends ConceptRef(name)
    
    case class CartesianProduct (left : ConceptRef, right : ConceptRef) extends ConceptRef(f"($left) * ($right)")
}
