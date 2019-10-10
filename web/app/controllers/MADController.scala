package controllers

import javax.inject._
import play.api.mvc._
import play.twirl.api._
import akka.stream.scaladsl._

import io.github.ProjectSophus.mad._
import questions._
import model._


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
    
    def question(hash : String) = Action {
        val h = hash.toInt
        
        val questions = Question.questions(model)
        val q = questions.find(_.hashCode() == h).get
        
        Ok(views.html.question(q))
        
    }
    
}
