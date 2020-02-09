package io.github.ProjectSophus.mad.lang.interpolation

import scala.util.parsing.combinator._

import scala.util.matching._

import Interpolation.InterpolationData

case class Interpolation(data : Seq[InterpolationData])

object Interpolation extends RegexParsers {
    
    def getInterpolation(str : String) : Interpolation = parseAll(interpolation, str) match {
        case Success(result, _) => result
        
        case Failure(msg, next) => throw InterpolationException(msg, next.pos)
        case Error(msg, next) => throw InterpolationException(msg, next.pos)
    }
    
    abstract sealed class InterpolationData
    case class InterpolationRaw (str : String) extends InterpolationData
    case class InterpolationVariable (varname : String) extends InterpolationData
    
    def varCurly : Parser[InterpolationVariable] = raw"(?<!\\)(?:\\\\)*\$$\{([^\}]+)\}".r ^^ (str => InterpolationVariable(str.drop(2).dropRight(1)))
    def varNoCurly : Parser[InterpolationVariable] = raw"(?<!\\)(?:\\\\)*\$$([A-Za-z]+)".r ^^ (str => InterpolationVariable(str.drop(1)))
    
    def variable : Parser[InterpolationVariable] = varCurly | varNoCurly
    
    def rawNoDollar : Parser[InterpolationRaw] = raw"[^\$$]+".r ^^ (InterpolationRaw(_))
    def doubleDollar : Parser[InterpolationRaw] = raw"\$$\$$".r ^^^ (InterpolationRaw("$"))
    
    def raw : Parser[InterpolationRaw] = rawNoDollar | doubleDollar
    
    def interpolation : Parser[Interpolation] = (variable | raw).* ^^ (Interpolation(_))
    
}
