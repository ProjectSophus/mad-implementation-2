package io.github.ProjectSophus.mad.lang


import io.github.ProjectSophus.mad.information.{Information => Inf}

import parser._


object Compiler {
    import AST._
    
    def compileASTtoInformation(ast : AST) : Seq[Inf] = {
        val declarations = ast.expand match {case Module(declarations @ _*) => declarations}
        
        val info = for {
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
            
            case x => throw CompilerException(s"Don't know what to do with $info")
        }
        
        info.flatten
    }
}
