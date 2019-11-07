package io.github.ProjectSophus.mad.machinetype

abstract sealed class MachineType (val signature : Signature, val plural : String) {
    override def hashCode() = MachineType.machineTypes.indexOf(this)
}

object MachineType {
    import Signature._
    
    val machineTypes = Seq(
        Representation,
        Operation,
        Function,
        Relation,
        Property,
        Example
    )
    
    case object Representation extends MachineType (T -> REPR, "Representations")
    case object Operation extends MachineType (T * T -> T, "Operations")
    case object Function extends MachineType (T -> T, "Functions")
    case object Relation extends MachineType (T * T -> BOOL, "Relations")
    case object Property extends MachineType (T -> BOOL, "Properties")
    case object Example extends MachineType (STAR -> T, "Examples")
}
