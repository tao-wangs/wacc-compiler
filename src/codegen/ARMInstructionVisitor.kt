package codegen

import codegen.instr.*
import codegen.instr.operand2.Immediate
import codegen.instr.operand2.ImmediateChar
import codegen.instr.operand2.ImmediateOffset
import codegen.instr.operand2.PreImmediateOffset
import codegen.instr.operand2.ZeroOffset
import codegen.instr.operand2.RegisterOffset
import codegen.instr.operand2.PreRegisterOffset
import codegen.instr.SBool
import codegen.instr.loadable.Msg
import codegen.instr.register.GP
import codegen.instr.register.LR
import codegen.instr.register.PC
import codegen.instr.register.SP
import codegen.utils.SaveRegisters

class ARMInstructionVisitor : InstructionVisitor {
    override fun visitInstructions(instr: List<Instruction>): String {
        val assembly = instr.map { instruction -> instruction.accept(this) }.reduce { instr1, instr2 -> instr1 + "\n" + instr2 }
        return "PUSH {lr}\n$assembly\nLDR r0, =0\nPOP {pc}"
    }

    override fun visitTest(x: Test): String {
        return "TST${x.cond.accept(this)} ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitTestEquiv(x: TestEquiv): String {
        return "TEQ${x.cond.accept(this)} ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitAnd(x: And): String {
        return "AND${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitXor(x: Xor): String {
        return "XOR${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitOr(x: Or): String {
        return "ORR${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitAdd(x: Add): String {
        return "ADD${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitSub(x: Subtract): String {
        return "SUB${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitRevSub(x: ReverseSubtract): String {
        return "RSB${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitMul(x: Multiply): String {
        return "SMULL${x.cond.accept(this)}${x.s.accept(this)} ${x.RdHi.accept(this)},${x.RdLo.accept(this)}, ${x.Rm.accept(this)}, ${x.Rs.accept(this)}\n"
    }

    override fun visitBranch(x: Branch): String {
        return "B${x.cond.accept(this)} ${x.dest}\n"
    }

    override fun visitBranchWithLink(x: BranchWithLink): String {
        return "BL${x.cond.accept(this)} ${x.dest}\n"
    }

    override fun visitMove(x: Move): String {
        return "MOV${x.cond.accept(this)}${x.s.accept(this)} ${x.Rd.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitCompare(x: Compare): String {
        return "CMP${x.cond.accept(this)} ${x.Rn.accept(this)}, ${x.operand2.accept(this)}\n"
    }

    override fun visitLoad(x: Load): String {
        return "LDR${x.cond.accept(this)} ${x.Rd.accept(this)}, ${x.operand.loadAccept(this)}\n"
    }

    override fun visitLoadByte(x: LoadByte): String {
        return "LDR${x.cond.accept(this)}B ${x.Rd.accept(this)}, ${x.operand.loadAccept(this)}\n"
    }

    override fun visitStore(x: Store): String {
        return "STR${x.cond.accept(this)} ${x.Rd.accept(this)}, ${x.operand.loadAccept(this)}\n"
    }

    override fun visitStoreByte(x: StoreByte): String {
        return "STR${x.cond.accept(this)}B ${x.Rd.accept(this)}, ${x.operand.loadAccept(this)}\n"
    }

    override fun visitPush(x: Push): String {
        return "PUSH ${SaveRegisters.formatRegList(x.reglist, this)}\n"
    }

    override fun visitPop(x: Pop): String {
        return "POP ${SaveRegisters.formatRegList(x.reglist, this)}\n"
    }

    override fun visitMod(x: Mod): String {
        return "BL __aeabi_idivmod\n"
    }

    override fun visitDiv(x: Div): String {
        return "BL __aeabi_idiv\n"
    }

    // partial visitor functions (returns part of an instruction, not a complete instruction)
    override fun visitGPRegister(x: GP): String {
        return "r" + x.id.toString()
    }

    override fun visitPCRegister(x: PC): String {
        return "r15"
    }

    override fun visitLRRegister(x: LR): String {
        return "r14"
    }

    override fun visitSPRegister(x: SP): String {
        return "r13"
    }

    override fun visitImmediate(x: Immediate): String {
        return "#0x" + Integer.toHexString(x.value)
    }

    override fun visitImmediateChar(x: ImmediateChar): String {
        return "#" + x.value
    }

    override fun visitLabel(x: Label): String {
        return x.name + ":"
    }

    override fun loadImmediate(x: Immediate): String {
        return "=" + Integer.toHexString(x.value)
    }

    override fun loadMsg(x: Msg): String {
        return "=" + x.s
    }

    override fun visitImmediateOffset(x: ImmediateOffset): String {
        return "[${x.r.accept(this)}, #${x.value.accept(this)}]"
    }

    override fun loadImmediateOffset(x: ImmediateOffset): String {
        return visitImmediateOffset(x)
    }

    override fun visitZeroOffset(x: ZeroOffset): String {
        return "[${x.r.accept(this)}]"
    }

    override fun loadZeroOffset(x: ZeroOffset): String {
        return visitZeroOffset(x)
    }

    override fun visitRegisterOffset(x: RegisterOffset): String {
        return "[${x.r.accept(this)}, ${x.offset.accept(this)}]"
    }

    override fun loadRegisterOffset(x: RegisterOffset): String {
        return visitRegisterOffset(x)
    }

    override fun visitPreRegisterOffset(x: PreRegisterOffset): String {
        return "[${x.r.accept(this)}, ${x.offset.accept(this)}]!"
    }

    override fun loadPreRegisterOffset(x: PreRegisterOffset): String {
        return visitPreRegisterOffset(x)
    }

    override fun visitPreImmediateOffset(x: PreImmediateOffset): String {
        return "[${x.r.accept(this)}, #${x.value.accept(this)}]!"
    }

    override fun loadPreImmediateOffset(x: PreImmediateOffset): String {
        return visitPreImmediateOffset(x)
    }

    override fun visitSBool(x: SBool): String {
        return if (x.bool) "S" else ""
    }

    override fun visitCond(x: Cond): String {
        return when (x.cond) {
            Condition.EQ -> "EQ"
            Condition.NE -> "NE"
            Condition.HSCS -> "HSCS"
            Condition.LOCC -> "LOCC"
            Condition.MI -> "MI"
            Condition.PL -> "PL"
            Condition.VS -> "VS"
            Condition.VC -> "VC"
            Condition.HI -> "HI"
            Condition.LS -> "LS"
            Condition.GE -> "GE"
            Condition.LT -> "LT"
            Condition.GT -> "GT"
            Condition.LE -> "LE"
            Condition.AL -> ""
        }

    }
}
