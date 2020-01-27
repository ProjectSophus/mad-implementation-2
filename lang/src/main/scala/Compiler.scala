package io.github.ProjectSophus.mad.lang


import io.github.ProjectSophus.mad._
import information.{Information => Inf}
import reference._

import parser._


object Compiler {
    import AST._
    
    def compileASTtoInformation(ast : AST) : Seq[Inf] = {
        val declarations = ast.expand match {case Module(declarations @ _*) => declarations}
        
        val info : Seq[Seq[Inf]] = for {
            declaration <- declarations
        } yield declaration match {
            case IsConcept(Seq(Object(name))) => Seq(
                Inf.NewObject(name, okayIfExists = true),
                Inf.IsConcept(name)
            )
            
            case IsExample(Seq(Object(conc)), Seq(Object(obj))) => Seq(
                Inf.NewObject(obj, okayIfExists = true),
                Inf.IsExampleOf(conc, obj)
            )
            
            case IsAntiexample(Seq(Object(conc)), Seq(Object(obj))) => Seq(
                Inf.NewObject(obj, okayIfExists = true),
                Inf.IsAntiexampleOf(conc, obj)
            )
            
            case IsRepresentation(Seq(Object(conc)), Seq(Object(rep))) => Seq(
                Inf.NewObject(rep, okayIfExists = true),
                Inf.IsRepresentation(rep),
                Inf.ObjectRelevant(conc, rep)
            )
            
            case IsRelevant(Seq(Object(obj)), Seq(Object(to))) => Seq(
                Inf.ObjectRelevant(to, obj) // TODO: Switch?
            )
            
            case HasDescription(Seq(Object(obj)), desc) => Seq(
                Inf.Description(obj, desc)
            )
            
            case IsMachineTypeOn(mt, Seq(Object(conc)), Seq(Object(machine))) => {
                
                val (domain, codomain) = mt.machinetype.signature.createConceptRefs(ConceptRef.BasicRef(conc))
                
                val macname = f"$conc.$machine"
                
                Seq(
                    Inf.NewObject(macname),
                    Inf.IsMachine(macname, domain, codomain),
                    Inf.ObjectRelevant(conc, macname)
                )
            }
            
            case IsMachine(domain, codomain, Seq(Object(machine))) => {
                
                if (domain.length == 0) throw CompilerException("Domain must have at least one input!")
                if (codomain.length == 0) throw CompilerException("Codomain must have at least one output!")
                
                def toConceptRef(seq : Seq[Object]) = seq.map(obj => ConceptRef.BasicRef(obj.name)).reduceLeft(ConceptRef.CartesianProduct)
                
                val sdomain = toConceptRef(domain)
                val scodomain = toConceptRef(codomain)
                
                Seq(
                    Inf.NewObject(machine),
                    Inf.IsMachine(machine, sdomain, scodomain)
                )
            }
            
            case x => throw CompilerException(s"Don't know what to do with $declaration")
        }
        
        info.flatten
    }
}
