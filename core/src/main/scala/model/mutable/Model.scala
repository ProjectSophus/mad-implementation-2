package io.github.ProjectSophus.mad.model.mutable

import io.github.ProjectSophus.mad._

import collection._

case class Model (
    objects : mutable.Buffer[Object]
) {
    def toModel : model.immutable.Model = model.immutable.Model(
        objects.toSeq.map(_.toObject)
    )
    
    def objects(name : String) : Object = objects.toSeq.find(_.name == name).get
}

object Model {
    def apply() : Model = Model(mutable.Buffer())
}
