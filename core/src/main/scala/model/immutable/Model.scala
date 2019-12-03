package io.github.ProjectSophus.mad.model.immutable

case class Model (objects : Seq[Object]) {
    def concepts : Seq[Object] = objects.filter(_.hasStructure[Structure.Concept])
    def machines : Seq[Object] = objects.filter(_.hasStructure[Structure.Machine])
    
    def obj(name : String) : Object = objects.find(_.name == name).get
}

object Model {
    def apply() : Model = Model(Seq())
}
