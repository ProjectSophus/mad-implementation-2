package io.github.ProjectSophus.mad.model.immutable

import scala.reflect._

case class Object(name : String, description : Option[String], structure : Option[Option[Structure]], relatedObjects : Seq[String]){
    def hasStructure[T <: Structure](implicit ct : ClassTag[T]) = structure.flatten.fold(false)(classTag[T].runtimeClass.isInstance)
    def getStructure[T <: Structure] : T = structure.get.get.asInstanceOf[T]
}
