package io.github.ProjectSophus.mad.reference

abstract sealed class ConceptRef

object ConceptRef {
    case class BasicRef (uid : String) extends ConceptRef
    
    case class CartesianProduct (left : ConceptRef, right : ConceptRef) extends ConceptRef
}
