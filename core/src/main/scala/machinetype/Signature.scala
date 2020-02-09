package io.github.ProjectSophus.mad.machinetype

import io.github.ProjectSophus.mad._
import reference._
import model.immutable._

import Signature._

case class Signature(domain : ConceptPattern, codomain : ConceptPattern) {
    def createConceptRefs(t : ConceptRef) : (ConceptRef, ConceptRef) = domain.createConceptRef(t) -> codomain.createConceptRef(t)
}

object Signature {
    import scala.language.implicitConversions
    
    implicit def pairToSignature (pair : (ConceptPattern, ConceptPattern)) : Signature = Signature(pair._1, pair._2)
    
    abstract sealed class ConceptPattern {
        def createConceptRef (t : ConceptRef) : ConceptRef
        
        def * (other : ConceptPattern) : ConceptPattern = PatternProduct(this, other)
    }
    
    case object T extends ConceptPattern { def createConceptRef(t : ConceptRef) = t }
    case object BOOL extends ConceptPattern { def createConceptRef(t : ConceptRef) = ConceptRef.BasicRef("Booleans") }
    
    case class PatternProduct (left : ConceptPattern, right : ConceptPattern) extends ConceptPattern {
        def createConceptRef(t : ConceptRef) = ConceptRef.CartesianProduct(left.createConceptRef(t), right.createConceptRef(t))
    }
    
    def apply (machine : Structure.Machine, signature : Signature, t : ConceptRef) : Boolean = apply(machine.domain, signature.domain, t) & apply(machine.codomain, signature.codomain, t)
    
    def apply (cr : ConceptRef, cp : ConceptPattern, t : ConceptRef) : Boolean = cp match {
        case T => cr == t
        case BOOL => cr == ConceptRef.BasicRef("BOOL")
        case PatternProduct(pl, pr) => cr match {
            case ConceptRef.CartesianProduct(l, r) => apply(l, pl, t) & apply(r, pr, t)
            case _ => false
        }
    }
    
}
