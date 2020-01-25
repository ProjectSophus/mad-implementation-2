package io.github.ProjectSophus.mad.lang

import io.github.ProjectSophus.mad.machinetype.{MachineType => MT}

abstract sealed class MachineType(val machinetype : MT)

object MachineType {
    
    case object Operation extends MachineType(MT.Operation)
    case object Function extends MachineType(MT.Function)
    case object Relation extends MachineType(MT.Relation)
    case object Property extends MachineType(MT.Property)
    
}
