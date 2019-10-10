package controllers

import javax.inject._
import play.api.mvc._
import play.twirl.api._
import akka.stream.scaladsl._


@Singleton
class MADController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    def index() = Action {
        Ok(views.html.index())
    }

}
