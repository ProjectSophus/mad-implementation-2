package io.github.ProjectSophus.mad.machinetype

abstract sealed class MachineType (val signature : Signature) {
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
    
    case object Representation extends MachineType (T -> REPR)
    case object Operation extends MachineType (T * T -> T)
    case object Function extends MachineType (T -> T)
    case object Relation extends MachineType (T * T -> BOOL)
    case object Property extends MachineType (T -> BOOL)
    case object Example extends MachineType (STAR -> T)
}
