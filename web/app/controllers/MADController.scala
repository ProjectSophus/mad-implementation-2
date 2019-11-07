package controllers

import javax.inject._
import play.api.mvc._

import io.github.ProjectSophus.mad._
import questions._
import interpreter._
import machinetype._
import reference._
import memory._


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
        
        Ok(views.html.question(q, answers))
        
    }
    
    def concepts() = Action {
        Ok(views.html.concepts(model.concepts))
    }
    
    def concept(uid : String) = Action {
        
        val concept = model.concepts(uid)
        
        val allMachines = for (machinetype <- MachineType.machineTypes) yield machinetype -> (for {
            (muid, machine) <- concept.relatedMachines.map{ case MachineRef.BasicRef(ref) => ref -> model.machines(ref)}
            if Signature(machine, machinetype.signature, ConceptRef.BasicRef(uid))
        } yield machine).toSeq
        
        val machines = allMachines.filter(!_._2.isEmpty)
        
        Ok(views.html.concept(concept, machines))
    }
    
    def machines() = Action {
        Ok(views.html.machines(model.machines))
    }
    
    def machine(uid : String) = Action {
        
        val machine = model.machines(uid)
        
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
                case Text => element
                case LongText => element
            }
        }) : _*)
        
        val info = Interpreter(q, data)
        
        memory.applyInformation(q, info)
        
        Redirect(routes.MADController.question(hash))
    }
    
}
