package io.github.ProjectSophus.mad.machinetype

abstract sealed class MachineType (val signature : Signature)

object MachineType {
    import Signature._
    
    case object Representation extends MachineType (T -> REPR)
    case object Operation extends MachineType (T * T -> T)
    case object Function extends MachineType (T -> T)
    case object Relation extends MachineType (T * T -> BOOL)
    case object Property extends MachineType (T -> BOOL)
    case object Example extends MachineType (STAR -> T)
}
