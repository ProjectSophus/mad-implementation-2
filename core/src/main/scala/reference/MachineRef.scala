package io.github.ProjectSophus.mad.reference

abstract sealed class MachineRef

object MachineRef {
    case class BasicRef (uid : String) extends MachineRef
}
