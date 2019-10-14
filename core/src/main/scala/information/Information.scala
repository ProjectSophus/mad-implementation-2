package io.github.ProjectSophus.mad.information

abstract sealed class Information (override val toString : String)

object Information {
    case class NewConcept(uid : String, name : String) extends Information(f"There is a concept named $name")
}
