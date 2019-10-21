package io.github.ProjectSophus.mad.model.immutable

case class Model (
    concepts : Map[String, Concept],
    machines : Map[String, Machine]
)

object Model {
    def apply() : Model = Model(Map(), Map())
}
