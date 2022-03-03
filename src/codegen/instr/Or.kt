package codegen.instr

import codegen.instr.operand2.Operand2
import codegen.instr.register.Register

class Or(
    val Rd: Register,
    val Rn: Register,
    val operand2: Operand2,
    cond: Cond = Cond(Condition.AL),
    s: SFlag = SFlag(false)
) : Instruction(cond, s) {
    override fun <T> accept(v: InstructionVisitor<T>): T {
        return v.visitOr(this)
    }
}
