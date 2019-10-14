package io.github.ProjectSophus.mad.reference

abstract sealed class ConceptRef(override val toString : String)

object ConceptRef {
    case class BasicRef (uid : String) extends ConceptRef(uid)
    
    case class CartesianProduct (left : ConceptRef, right : ConceptRef) extends ConceptRef(f"($left) * ($right)")
}
