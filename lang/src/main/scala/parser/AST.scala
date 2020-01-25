package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang._

abstract sealed class AST {def expand : AST}

object AST {
    
    case class Module(decs : Declaration*) extends AST {
        def expand : Module = Module((for {
            decs1 <- decs
            dec <- expandDec(decs1)
        } yield dec) : _*)
    }
    
    abstract sealed class Declaration
    
    
    case class IsConcept(obj : Seq[Object]) extends Declaration
    case class IsExample(conc : Seq[Object], obj : Seq[Object]) extends Declaration
    case class IsAntiexample(conc : Seq[Object], obj : Seq[Object]) extends Declaration
    case class IsRepresentation(conc : Seq[Object], rep : Seq[Object]) extends Declaration
    case class IsMachineTypeOn(machinetype : MachineType, conc : Seq[Object], machine : Seq[Object]) extends Declaration
    case class IsMachine(domain : Seq[Object], codomain : Seq[Object], machine : Seq[Object]) extends Declaration
    
    case class Object(name : String)
    
    def expandDec (dec : Declaration) : Seq[Declaration] = dec match {
        case IsConcept(obj) => for (sobj <- obj) yield IsConcept(Seq(sobj))
        case IsExample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsExample(Seq(sconc), Seq(sobj))
        case IsAntiexample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsAntiexample(Seq(sconc), Seq(sobj))
        case IsRepresentation(conc, rep) => for (sconc <- conc; srep <- rep) yield IsRepresentation(Seq(sconc), Seq(srep))
        case IsMachineTypeOn(machinetype, conc, machine) => for (sconc <- conc; smachine <- machine) yield IsMachineTypeOn(machinetype, Seq(sconc), Seq(smachine))
        case IsMachine(domain, codomain, machine) => for (smachine <- machine) yield IsMachine(domain, codomain, Seq(smachine))
    }
    
}
