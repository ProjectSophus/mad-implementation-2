package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang._

abstract sealed class AST

object AST {
    
    case class Module(decs : Declaration*) extends AST
    
    abstract sealed class Declaration
    
    
    case class IsConcept(obj : Object) extends Declaration
    case class IsExample(conc : Object, obj : Object) extends Declaration
    case class IsAntiExample(conc : Object, obj : Object) extends Declaration
    case class IsRepresentation(conc : Object, rep : Object) extends Declaration
    case class IsMachineTypeOn(machinetype : MachineType, conc : Object, machine : Object) extends Declaration
    case class IsMachine(domain : Seq[Object], codomain : Seq[Object], machine : Object) extends Declaration
    
    case class Object(name : Seq[String]) extends Seq[Object]{
        override def toSeq = name.map(str => Object(Seq(str)))
        
        override def iterator = toSeq.iterator
        override def apply(idx : Int) = toSeq(idx)
        override def length = toSeq.length
    }
    
    
    
    def expand (dec : Declaration) : Seq[Declaration] = dec match {
        case IsConcept(obj) => for (sobj <- obj) yield IsConcept(sobj)
        case IsExample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsExample(sconc, sobj)
        case IsAntiExample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsAntiExample(sconc, sobj)
        case IsRepresentation(conc, rep) => for (sconc <- conc; srep <- rep) yield IsRepresentation(sconc, srep)
        case IsMachineTypeOn(machinetype, conc, machine) => for (sconc <- conc; smachine <- machine) yield IsMachineTypeOn(machinetype, sconc, smachine)
        case IsMachine(domain, codomain, machine) => for (smachine <- machine) yield IsMachine(domain, codomain, smachine)
    }
    
}
