package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad._

case class Model (objects : Seq[Object]) {
    def concepts : Seq[Object] = objects.filter(_.hasStructure[Structure.Concept])
    def machines : Seq[Object] = objects.filter(_.hasStructure[Structure.Machine])
    
    def obj(name : String) : Object = objects.find(_.name == name).getOrElse(throw MADException.ObjectNotFound(name))
}

object Model {
    def apply() : Model = Model(Seq())
}
