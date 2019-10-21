package io.github.ProjectSophus.mad.model

import collection._

case class Model (
    concepts : mutable.Map[String, Concept],
    machines : mutable.Map[String, Machine]
)

object Model {
    def apply() : Model = Model(mutable.Map(), mutable.Map())
}
