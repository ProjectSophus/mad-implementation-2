package io.github.ProjectSophus.mad.lang


import io.github.ProjectSophus.mad._
import information.{Information => Inf}
import reference._

import parser._


object Compiler {
    import AST._
    
    import collection.mutable.{HashMap, Buffer}
    
    case class Scope (val variables : HashMap[String, String], val templates : Buffer[CreateTemplate]) {
        def createSubScope() : Scope = Scope(
            HashMap[String, String]() ++= variables,
            Buffer[CreateTemplate]() ++= templates
        )
    }
    
    object Scope {
        def apply() : Scope = Scope(HashMap[String, String](), Buffer[CreateTemplate]())
    }
    
    object ExtractExpression {
        def unapply(obj : Expression)(implicit scope : Scope) : Option[String] = Some(obj match {
            case ConcreteExpression(str) => str
            case VariableExpression(varname) => scope.variables.getOrElse(varname, throw CompilerException(s"Variable not found: $varname"))
            case InterpolationExpression(str) => {
                import interpolation._
                import Interpolation._
                
                val Interpolation(data) = getInterpolation(str)
                
                data.map{
                    case InterpolationRaw(raw) => raw
                    case InterpolationVariable(varname) => scope.variables.getOrElse(varname, throw CompilerException(s"Variable in interpolation not found: $varname"))
                }.mkString
            }
        })
    }
    
    def compileASTtoInformation(ast : AST, scope : Scope = Scope()) : Seq[Inf] = {
        implicit val implicitScope = scope
        
        val code = ast.expand.asInstanceOf[ScopedCode]
        
        val info : Seq[Seq[Inf]] = for {
            command <- code.commands
        } yield command match {
            case dec : Declaration => dec match {
                case IsConcept(Seq(ExtractExpression(name))) => Seq(
                    Inf.NewObject(name, okayIfExists = true),
                    Inf.IsConcept(name)
                )
                
                case IsExample(Seq(ExtractExpression(conc)), Seq(ExtractExpression(obj))) => Seq(
                    Inf.NewObject(obj, okayIfExists = true),
                    Inf.IsExampleOf(conc, obj)
                )
                
                case IsAntiexample(Seq(ExtractExpression(conc)), Seq(ExtractExpression(obj))) => Seq(
                    Inf.NewObject(obj, okayIfExists = true),
                    Inf.IsAntiexampleOf(conc, obj)
                )
                
                case IsRepresentation(Seq(ExtractExpression(conc)), Seq(ExtractExpression(rep))) => Seq(
                    Inf.NewObject(rep, okayIfExists = true),
                    Inf.IsRepresentation(rep),
                    Inf.ObjectRelevant(conc, rep)
                )
                
                case IsRelevant(Seq(ExtractExpression(obj)), Seq(ExtractExpression(to))) => Seq(
                    Inf.ObjectRelevant(to, obj),
                    Inf.ObjectRelevant(obj, to)
                )
                
                case HasDescription(Seq(ExtractExpression(obj)), desc) => Seq(
                    Inf.Description(obj, desc)
                )
                
                case IsMachineTypeOn(mt, Seq(ExtractExpression(conc)), Seq(ExtractExpression(machine))) => {
                    
                    val (domain, codomain) = mt.machinetype.signature.createConceptRefs(ConceptRef.BasicRef(conc))
                    
                    val macname = f"$conc.$machine"
                    
                    Seq(
                        Inf.NewObject(macname),
                        Inf.IsMachine(macname, domain, codomain),
                        Inf.ObjectRelevant(conc, macname)
                    )
                }
                
                case IsMachine(domain, codomain, Seq(ExtractExpression(machine))) => {
                    
                    if (domain.length == 0) throw CompilerException("Domain must have at least one input!")
                    if (codomain.length == 0) throw CompilerException("Codomain must have at least one output!")
                    
                    def toConceptRef(seq : Seq[Expression]) = seq.map{
                        case ExtractExpression(name) => ConceptRef.BasicRef(name)
                    }.reduceLeft(ConceptRef.CartesianProduct)
                    
                    val sdomain = toConceptRef(domain)
                    val scodomain = toConceptRef(codomain)
                    
                    Seq(
                        Inf.NewObject(machine),
                        Inf.IsMachine(machine, sdomain, scodomain)
                    )
                }
                
                case IsStatement(ExtractExpression(obj), str) => Seq(
                    Inf.NewObject(obj, okayIfExists = true),
                    Inf.IsStatement(obj, str)
                )
                
                case x => throw CompilerException(s"Don't know what to do with declaration $x")
            }
            
            case x : CreateTemplate => {
                scope.templates += x
                Seq()
            }
            
            case UseTemplate(Seq(ExtractExpression(name)), Seq(params)) => {
                val template = scope.templates.find(_.name == name).getOrElse{
                    throw CompilerException(s"Couldn't find template $name. Did you import it?")
                }
                
                val CreateTemplate(_, paramNames, code) = template
                
                if (paramNames.length != params.length) throw CompilerException(s"Amount of parameters in template $name wrong! There are supposed to be ${paramNames.length} but there are ${params.length} supplied!")
                
                val resolvedParams = params.map{
                    case ExtractExpression(param) => param
                }
                
                val newVariables = paramNames zip resolvedParams
                
                val newscope = scope.createSubScope()
                newscope.variables ++= newVariables
                
                compileASTtoInformation(code, newscope)
            }
            
            case x => throw CompilerException(s"Don't know what to do with command $x")
        }
        
        info.flatten
    }
}
