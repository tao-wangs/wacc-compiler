package parse.stat

import codegen.ASTNode
import codegen.ASTVisitor
import codegen.instr.Instruction
import codegen.instr.loadable.Loadable
import parse.symbols.Int
import parse.symbols.Type

class Decrement(val lhs: AssignLhs, val decrAmount: kotlin.Int) :  Stat(), AssignLhs {

    init {
        if (!(lhs.type() is Int)) {
            ErrorHandler.printErr(
                ErrorType.SEMANTIC,
                "Incompatible type at $lhs (expected: Int, actual: ${lhs.type()}"
            )
        }
    }

    override fun accept(v: ASTVisitor): List<Instruction> {
        return v.visitDecrement(this)
    }

    override fun type(): Type {
        return Int
    }

    override fun acceptLhs(v: ASTVisitor): Pair<List<Instruction>, Loadable> {
        return v.visitDecrementLhs(this)
    }

}
