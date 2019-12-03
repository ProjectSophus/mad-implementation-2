package io.github.ProjectSophus.mad.machinetype

abstract sealed class MachineType (val signature : Signature, val plural : String) {
    override def hashCode() = MachineType.machineTypes.indexOf(this)
}

object MachineType {
    import Signature._
    
    val machineTypes = Seq(
        Operation,
        Function,
        Relation,
        Property
    )
    
    case object Operation extends MachineType (T * T -> T, "Operations")
    case object Function extends MachineType (T -> T, "Functions")
    case object Relation extends MachineType (T * T -> BOOL, "Relations")
    case object Property extends MachineType (T -> BOOL, "Properties")
}
