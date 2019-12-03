package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._

import scala.reflect._

case class Object(name : String, var description : Option[String] = None, var structure : Option[Option[Structure]] = None) {
    def toObject : immutable.Object = immutable.Object(name, description, structure.map(_.map(_.toStructure)))
    
    def hasStructure[T <: Structure](implicit ct : ClassTag[T]) = structure.flatten.fold(false)(classTag[T].runtimeClass.isInstance)
    def getStructure[T <: Structure] : T = structure.get.get.asInstanceOf[T]
}
