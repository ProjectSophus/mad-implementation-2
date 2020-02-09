package io.github.ProjectSophus.mad.lang.parser

import io.github.ProjectSophus.mad.lang._

abstract sealed class AST {def expand : AST}

object AST {
    
    case class ScopedCode(commands : Seq[Command]) extends AST {
        def expand : ScopedCode = ScopedCode(for {
            command1 <- commands
            command <- expandCommand(command1)
        } yield command)
    }
    
    abstract sealed class Command
    abstract sealed class Declaration extends Command
    
    
    case class IsConcept(obj : Seq[Expression]) extends Declaration
    case class IsExample(conc : Seq[Expression], obj : Seq[Expression]) extends Declaration
    case class IsAntiexample(conc : Seq[Expression], obj : Seq[Expression]) extends Declaration
    case class IsRepresentation(conc : Seq[Expression], rep : Seq[Expression]) extends Declaration
    case class IsRelevant(obj : Seq[Expression], to : Seq[Expression]) extends Declaration
    case class HasDescription(obj : Seq[Expression], desc : String) extends Declaration
    case class IsMachineTypeOn(machinetype : MachineType, conc : Seq[Expression], machine : Seq[Expression]) extends Declaration
    case class IsMachine(domain : Seq[Expression], codomain : Seq[Expression], machine : Seq[Expression]) extends Declaration
    case class IsStatement(obj : Expression, str : String) extends Declaration

    
    case class CreateTemplate(name : String, params : Seq[String], code : ScopedCode) extends Command
    
    case class UseTemplate(name : Seq[Expression], params : Seq[Seq[Expression]]) extends Command
    
    sealed abstract class Expression
    case class ConcreteExpression(name : String) extends Expression
    case class VariableExpression(varname : String) extends Expression
    
    def expandCommand (command : Command) : Seq[Command] = command match {
        case IsConcept(obj) => for (sobj <- obj) yield IsConcept(Seq(sobj))
        case IsExample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsExample(Seq(sconc), Seq(sobj))
        case IsAntiexample(conc, obj) => for (sconc <- conc; sobj <- obj) yield IsAntiexample(Seq(sconc), Seq(sobj))
        case IsRepresentation(conc, rep) => for (sconc <- conc; srep <- rep) yield IsRepresentation(Seq(sconc), Seq(srep))
        case IsRelevant(obj, to) => for (sobj <- obj; sto <- to) yield IsRelevant(Seq(sobj), Seq(sto))
        case HasDescription(obj, desc) => for (sobj <- obj) yield HasDescription(Seq(sobj), desc)
        case IsMachineTypeOn(machinetype, conc, machine) => for (sconc <- conc; smachine <- machine) yield IsMachineTypeOn(machinetype, Seq(sconc), Seq(smachine))
        case IsMachine(domain, codomain, machine) => for (smachine <- machine) yield IsMachine(domain, codomain, Seq(smachine))
        case _ : IsStatement | _ : CreateTemplate => Seq(command)
        case UseTemplate(name, params) => for {sname <- name; sparams <- params} yield UseTemplate(Seq(sname), Seq(sparams))
    }
    
}
