package controllers

import javax.inject._
import play.api.mvc._
import play.twirl.api._
import akka.stream.scaladsl._

import io.github.ProjectSophus.mad._
import questions._
import model._
import interpreter._


@Singleton
class MADController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    val model = Model()

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
        
        Ok(views.html.answer(info))
    }
    
}
