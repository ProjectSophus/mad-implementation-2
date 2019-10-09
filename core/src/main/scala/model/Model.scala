package io.github.ProjectSophus.mad.model

case class Model (
    concepts : Map[String, Concept],
    machines : Map[String, Machine]
)

object Model {
    def apply() : Model = Model(Map(), Map())
}
