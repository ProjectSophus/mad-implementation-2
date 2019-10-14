package controllers

import javax.inject._
import play.api.mvc._

import io.github.ProjectSophus.mad._
import information._
import questions._
import model._
import interpreter._


@Singleton
class MADController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    var model = Model()

    def index() = Action {
        Ok(views.html.index())
    }
    
    def questions() = Action {
        
        val questions = Question.questions(model)
        
        Ok(views.html.questions(questions))
    }
    
    def question(hash : String) = Action { implicit request =>
        val h = hash.toInt
        
        val questions = Question.questions(model)
        val q = questions.find(_.hashCode() == h).get
        
        Ok(views.html.question(q))
        
    }
    
    def answer() = Action { implicit request =>
        val h = request.body.asFormUrlEncoded.get.apply("questionHash").head.toInt
        
        val questions = Question.questions(model)
        val q = questions.find(_.hashCode() == h).get
        
        val data : Map[String, Any] = Map((for ((label, inputtype) <- q.answer.elements) yield label -> {
            val element = request.body.asFormUrlEncoded.get.apply(label).head
            
            import Answer._
            
            inputtype match {
                case Text => element
            }
        }) : _*)
        
        val info = Interpreter(q, data)
        
        info foreach { information =>
            model = InformationAgent(information, model)
        }
        
        Ok(views.html.answer(info))
    }
    
}
