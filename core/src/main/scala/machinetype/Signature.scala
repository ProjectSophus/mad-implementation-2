package io.github.ProjectSophus.mad.machinetype

import io.github.ProjectSophus.mad._
import reference._
import model._

import Signature._

case class Signature(domain : ConceptPattern, codomain : ConceptPattern)

object Signature {
    import scala.language.implicitConversions
    
    implicit def pairToSignature (pair : (ConceptPattern, ConceptPattern)) : Signature = Signature(pair._1, pair._2)
    
    abstract sealed class ConceptPattern {
        def * (other : ConceptPattern) : ConceptPattern = PatternProduct(this, other)
    }
    
    case object T extends ConceptPattern
    case object REPR extends ConceptPattern
    case object BOOL extends ConceptPattern
    case object STAR extends ConceptPattern
    
    case class PatternProduct (left : ConceptPattern, right : ConceptPattern) extends ConceptPattern
    
    def apply (machine : Machine, signature : Signature, t : ConceptRef) : Boolean = apply(machine.domain, signature.domain, t) & apply(machine.codomain, signature.codomain, t)
    
    def apply (cr : ConceptRef, cp : ConceptPattern, t : ConceptRef) : Boolean = cp match {
        case T => cr == t
        case REPR => cr == ConceptRef.BasicRef("REPR")
        case BOOL => cr == ConceptRef.BasicRef("BOOL")
        case STAR => cr == ConceptRef.BasicRef("STAR")
        case PatternProduct(pl, pr) => cr match {
            case ConceptRef.CartesianProduct(l, r) => apply(l, pl, t) & apply(r, pr, t)
            case _ => false
        }
    }
    
}
