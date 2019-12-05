package controllers

import javax.inject._
import play.api.mvc._

import io.github.ProjectSophus.mad._
import questions._
import interpreter._
import machinetype._
import reference._
import memory._
import model.immutable._


@Singleton
class MADController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    val memory = Memory()
    def model = memory.getModel

    def index() = Action {
        Ok(views.html.index())
    }
    
    def questions() = Action {
        
        val questions = Question.questions(model)
        
        val questionsWithAmount = questions.map {q => q -> memory.getAnswersTo(q).length}
        
        Ok(views.html.questions(questionsWithAmount))
    }
    
    def question(hash : String) = Action { implicit request =>
        val h = java.lang.Long.parseLong(hash, 16).asInstanceOf[Int]
        
        val questions = Question.questions(model)
        val q = questions.find(_.hashCode() == h).get
        
        val answers = memory.getAnswersTo(q)
        
        Ok(views.html.question(q, answers, model))
        
    }
    
    def objects() = Action {
        Ok(views.html.objects("Objects", model.objects))
    }
    
    def concepts() = Action {
        Ok(views.html.objects("Concepts", model.concepts))
    }
    
    def machines() = Action {
        Ok(views.html.objects("Machines", model.machines))
    }
    
    def concept(uid : String) = Action {
        
        val concept = model.obj(uid)
        
        if(!concept.hasStructure[Structure.Concept]) throw MADException.RefNotAConcept(uid)
        
        val examples = concept.getStructure[Structure.Concept].examples
        
        val representations = for {
            (name, repr) <- concept.getStructure[Structure.Concept].relatedObjects.map{ case ref => ref -> model.obj(ref)}
            if repr.hasStructure[Structure.Representation]
        } yield name
            
        val allMachines = for {machinetype <- MachineType.machineTypes} yield machinetype.plural -> (for {
            (muid, machine) <- concept.getStructure[Structure.Concept].relatedObjects.map{ case ref => ref -> model.obj(ref)}
            if machine.hasStructure[Structure.Machine]
            if Signature(machine.getStructure[Structure.Machine], machinetype.signature, ConceptRef.BasicRef(uid))
        } yield muid).toSeq
        
        val machines = allMachines.filter(!_._2.isEmpty)
        
        Ok(views.html.concept(concept, Seq(("Examples", examples), ("Representations", representations)) ++ machines))
    }
    
    def machine(uid : String) = Action {
        
        val machine = model.obj(uid)
        
        Ok(views.html.machine(machine))
    }
    
    def answer(hash : String) = Action { implicit request =>
        val h = java.lang.Long.parseLong(hash, 16).asInstanceOf[Int]
        
        val questions = Question.questions(model)
        val q = questions.find(_.hashCode() == h).get
        
        val data : Map[String, Any] = Map((for ((label, inputtype) <- q.answer.elements) yield label -> {
            val element = request.body.asFormUrlEncoded.get.apply(label).head
            
            import Answer._
            
            inputtype match {
                case Label => ()
                case Text | LongText | Object | Machine => element
                case ConceptRef => ???
            }
        }) : _*)
        
        val info = Interpreter(q, data, model)
        
        memory.applyInformationSeq(q, info)
        
        Redirect(routes.MADController.question(hash))
    }
    
}
