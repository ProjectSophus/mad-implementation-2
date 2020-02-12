package io.github.ProjectSophus.mad.information

import io.github.ProjectSophus.mad._
import model.mutable._
import reference._
import memory._
import inference._

object InformationAgent {
    import Information._
    
    private def getAsConceptSafe(name : String, model : Model) : Structure.Concept = {
        if (!model.obj(name).hasStructure[Structure.Concept]) throw MADException.RefNotAConcept(name)
        model.obj(name).getStructure[Structure.Concept]
    }
    
    def apply (info : Information, memory : Memory, byInference : Information => Unit) : Unit = {
        val model = memory.getMutableModel
        val inferenceEngine = memory.inferenceEngine
        
        info match {
            case NewObject(name : String, okayIfExists : Boolean) => {
                if (model.objects.exists(_.name == name)) {
                    if(!okayIfExists) throw MADException.ObjectNameTaken(name)
                } else {
                    if (name == "") throw MADException.ObjectNameEmpty
                    model.objects += Object(name)
                }
            }
            
            case IsConcept(name : String) => {
                model.obj(name).structure = Some(Some(Structure.Concept()))
            }
            
            case IsMachine(name : String, domain : ConceptRef, codomain : ConceptRef) => {
                model.obj(name).structure = Some(Some(Structure.Machine(domain, codomain)))
            }
            
            case IsRepresentation(name) => {
                model.obj(name).structure = Some(Some(Structure.Representation()))
            }
            
            case IsExampleOf(concept, example) => {
                getAsConceptSafe(concept, model).examples += example
                ()
            }
            
            case IsAntiexampleOf(concept, antiexample) => {
                getAsConceptSafe(concept, model).antiexamples += antiexample
                ()
            }
            
            case ObjectRelevant(ob1, ob2) => {
                model.obj(ob1).relatedObjects += ob2
                model.obj(ob2).relatedObjects += ob1
                ()
            }
            
            case Description(name : String, description : String) => {
                model.obj(name).description = Some(description)
            }
            
            case IsStatement(obj : String, str : String) => {
                model.obj(obj).structure = Some(Some(Structure.Statement(str)))
            }
            
            case Generalization(gen, spec) => {
                val genc = getAsConceptSafe(gen, model)
                val specc = getAsConceptSafe(spec, model)
                
                specc.generalizations += gen
                genc.specializations += spec
                
                inferenceEngine.addInferer(new GeneralizationInferer(gen, spec))
                ()
            }
            
        }
        
        val infered = inferenceEngine.infer(info)
        infered.foreach(byInference)
    }
}
