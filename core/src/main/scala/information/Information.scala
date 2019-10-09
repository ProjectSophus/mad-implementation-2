package io.github.ProjectSophus.mad.information

sealed trait Information

object Information {
    case class NewConcept(uid : String, name : String) extends Information
}
