package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad._
import reference._

case class Concept (name : String, description : Option[String], relatedMachines : Seq[String])
