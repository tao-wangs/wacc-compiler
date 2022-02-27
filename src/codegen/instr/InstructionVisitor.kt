package codegen.instr

import codegen.instr.operand2.Immediate
import codegen.instr.operand2.ImmediateChar
import codegen.instr.operand2.ImmediateOffset
import codegen.instr.operand2.ZeroOffset
import codegen.instr.operand2.RegisterOffset
import codegen.instr.operand2.PreRegisterOffset
import codegen.instr.register.GP
import codegen.instr.register.LR
import codegen.instr.register.PC
import codegen.instr.register.SP
import codegen.instr.loadable.Msg

interface InstructionVisitor {
    fun visitTest(x: Test): String
    fun visitTestEquiv(x: TestEquiv): String
    fun visitAnd(x: And): String
    fun visitXor(x: Xor): String
    fun visitOr(x: Or): String
    fun visitAdd(x: Add): String
    fun visitSub(x: Subtract): String
    fun visitRevSub(x: ReverseSubtract): String
    fun visitMul(x: Multiply): String
    fun visitBranch(x: Branch): String
    fun visitBranchWithLink(x: BranchWithLink): String
    fun visitMove(x: Move): String
    fun visitCompare(x: Compare): String
    fun visitLoad(x: Load): String
    fun visitLoadByte(x: LoadByte): String
    fun visitGPRegister(x: GP): String
    fun visitPCRegister(x: PC): String
    fun visitLRRegister(x: LR): String
    fun visitSPRegister(x: SP): String
    fun visitImmediate(x: Immediate): String
    fun visitImmediateChar(x: ImmediateChar): String
    fun visitStore(x: Store): String
    fun visitStoreByte(x: StoreByte): String
    fun visitPush(x: Push): String
    fun visitPop(x: Pop): String
    fun visitMod(x: Mod): String
    fun visitDiv(x: Div): String
    fun visitLabel(x: Label): String
    fun loadImmediate(x: Immediate): String
    fun loadMsg(x: Msg): String
    fun visitImmediateOffset(x: ImmediateOffset): String
    fun loadImmediateOffset(x: ImmediateOffset): String
    fun visitZeroOffset(x: ZeroOffset): String
    fun loadZeroOffset(x: ZeroOffset): String
    fun visitRegisterOffset(x: RegisterOffset): String
    fun loadRegisterOffset(x: RegisterOffset): String
    fun visitPreRegisterOffset(x: PreRegisterOffset): String
    fun loadPreRegisterOffset(x: PreRegisterOffset): String
}
