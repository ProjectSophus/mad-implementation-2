package io.github.ProjectSophus.mad.model.immutable

import io.github.ProjectSophus.mad._

case class Concept (name : String, description : Option[String], relatedMachines : Seq[String])
