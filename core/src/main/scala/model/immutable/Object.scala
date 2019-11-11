package io.github.ProjectSophus.mad.model.immutable

case class Object(name : String, description : String, asConcept : Option[AsConcept], asMachine : Option[AsMachine])
