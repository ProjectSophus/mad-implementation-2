package io.github.ProjectSophus.mad.lang.interpolation

import scala.util.parsing.input._


case class InterpolationException(msg : String, pos : Position) extends Exception("Interpolation error in string: \n" + pos.longString)
