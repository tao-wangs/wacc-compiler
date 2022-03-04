package codegen.instr

import codegen.instr.operand2.Operand2
import codegen.instr.register.Register

class Test(val Rn: Register, val operand2: Operand2) : Instruction() {
    override fun <T> accept(v: InstructionVisitor<T>): T {
        return v.visitTest(this)
    }
}
