package io.github.ProjectSophus.mad.lang

abstract sealed class MachineType

object MachineType {
    
    case object Operation extends MachineType
    case object Function extends MachineType
    case object Relation extends MachineType
    case object Property extends MachineType
    
}
