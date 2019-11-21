package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._
import model._

case class Object(name : String, var description : String = null, var asConcept : Option[AsConcept] = null, var asMachine : Option[AsMachine] = null) {
    def toObject : immutable.Object = immutable.Object(name, description, if (asConcept == null) null else asConcept.map(_.toAsConcept), if(asMachine == null) null else asMachine.map(_.toAsMachine))
}
