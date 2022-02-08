package visitor

import antlr.*
import org.antlr.v4.runtime.tree.ParseTree
import expr.CharLiteral
import func.Function
import func.ParamList
import func.Parameter
import symbols.*
import symbols.Boolean
import symbols.Char
import symbols.Int
import symbols.String


class Visitor : WACCParserBaseVisitor<Identifier>() {

    var currentSymbolTable : SymbolTable = SymbolTable(null)
    var valid = true

    override fun visit(tree: ParseTree): Identifier? {
        currentSymbolTable.add("int", Int)
        currentSymbolTable.add("bool", Boolean)
        currentSymbolTable.add("char", Char)
        currentSymbolTable.add("null", Null)
        currentSymbolTable.add("string", String)
        return super.visit(tree)
    }

    override fun visitSkip(ctx: WACCParser.SkipContext): Identifier? {
        println("Skip statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitWhile(ctx: WACCParser.WhileContext): Identifier? {
        println("While statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitDeclaration(ctx: WACCParser.DeclarationContext): Identifier? {
        println("Declaration statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitExit(ctx: WACCParser.ExitContext): Identifier? {
        println("Exit statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitPrint(ctx: WACCParser.PrintContext): Identifier? {
        println("Print statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitPrintln(ctx: WACCParser.PrintlnContext): Identifier? {
        println("Println statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitComposition(ctx: WACCParser.CompositionContext): Identifier? {
        println("Composition statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitFree(ctx: WACCParser.FreeContext): Identifier? {
        println("Free statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitIf(ctx: WACCParser.IfContext): Identifier? {
        println("If statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitBegin(ctx: WACCParser.BeginContext): Identifier? {
        println("Begin statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitReturn(ctx: WACCParser.ReturnContext): Identifier? {
        println("Return statement visit")
        val result = visitChildren(ctx)
        return result
    }

    override fun visitAssign_lhs(ctx: WACCParser.Assign_lhsContext): Identifier? {
        println("Assign lhs visit")
        return visitChildren(ctx)
    }

    override fun visitAssignExpr(ctx: WACCParser.AssignExprContext): Identifier? {
        println("Assign rhs expr visit")
        return visitChildren(ctx)
    }

    override fun visitAssignPair(ctx: WACCParser.AssignPairContext): Identifier? {
        println("Assign rhs pair visit")
        return visitChildren(ctx)
    }

    override fun visitAssignPairElem(ctx: WACCParser.AssignPairElemContext): Identifier? {
        println("Assign rhs pair elem visit")
        return visitChildren(ctx)
    }

    override fun visitAssignFunc(ctx: WACCParser.AssignFuncContext): Identifier? {
        println("Assign rhs func visit")
        return visitChildren(ctx)
    }

    override fun visitArray_literal(ctx: WACCParser.Array_literalContext): Identifier? {
        println("Array literal visit")
        return visitChildren(ctx)
    }

    override fun visitBase_type(ctx: WACCParser.Base_typeContext): Identifier? {
        println("Base type visit")
        return visitChildren(ctx)
    }

    override fun visitPair_elem_type(ctx: WACCParser.Pair_elem_typeContext): Identifier? {
        println("Pair elem type visit")
        return visitChildren(ctx)
    }

    override fun visitPair_type(ctx: WACCParser.Pair_typeContext): Identifier? {
        println("Pair type visit")
        return visitChildren(ctx)
    }

    override fun visitType(ctx: WACCParser.TypeContext): Identifier? {
        println("Type visit")
        return visitChildren(ctx)
    }

    override fun visitFunc(ctx: WACCParser.FuncContext): Identifier? {
        println("Func visit")
        val funcSymbolTable = SymbolTable(currentSymbolTable)
        val funcName = ctx.IDENT().text
        val funcType = currentSymbolTable.lookupAll(ctx.type().text) as Type


        val paramList = mutableListOf<Parameter>()
        for (i in 0 until ctx.param_list().param().size) {
            var param = ctx.param_list().param()[i]
            var paramType = currentSymbolTable.lookupAll(param.type().text) as Type
            var paramName = param.IDENT().text

            //add to function's symbol table
            funcSymbolTable.add(paramName, paramType)

            //add to paramList
            paramList.add(Parameter(paramType, paramName))
        }

        val funcParam = ParamList(paramList)

        if (currentSymbolTable.lookup(funcName) != null) {
            System.err.println("$funcName already defined in current scope")
        }
        currentSymbolTable.add(funcName, Function(currentSymbolTable,funcName,funcType,funcParam,funcSymbolTable))

        //do we set currentSymbolTable to the function's SymbolTable?

        return visitChildren(ctx)
    }

    override fun visitArg_list(ctx: WACCParser.Arg_listContext): Identifier? {
        println("Arg list visit")
        return visitChildren(ctx)
    }

    override fun visitParam(ctx: WACCParser.ParamContext): Identifier? {
        println("Param visit")
        return visitChildren(ctx)
    }

    override fun visitParam_list(ctx: WACCParser.Param_listContext): Identifier? {
        println("Param list visit")
        return visitChildren(ctx)
    }

    override fun visitPair_elem(ctx: WACCParser.Pair_elemContext): Identifier? {
        println("Pair elem visit")
        return visitChildren(ctx)
    }

    override fun visitArray_elem(ctx: WACCParser.Array_elemContext): Identifier? {
        println("Array elem visit")
        return visitChildren(ctx)
    }

    override fun visitExclamation(ctx: WACCParser.ExclamationContext): Identifier? {
        println("Unary op exclamation visit")
        return visitChildren(ctx)
    }

    override fun visitLen(ctx: WACCParser.LenContext): Identifier? {
        println("Unary op len visit")
        return visitChildren(ctx)
    }

    override fun visitOrd(ctx: WACCParser.OrdContext): Identifier? {
        println("Unary op ord visit")
        return visitChildren(ctx)
    }
    override fun visitChr(ctx: WACCParser.ChrContext): Identifier? {
        println("Unary op chr visit")
        return visitChildren(ctx)
    }

    override fun visitProg(ctx: WACCParser.ProgContext): Identifier? {
        println("Prog visit")
        return visitChildren(ctx)
    }

    override fun visitPlus(ctx: WACCParser.PlusContext) : Identifier? {
        println("Plus binary op visit")
        return visitChildren(ctx)
    }

    override fun visitNeg(ctx: WACCParser.NegContext) : Identifier? {
        println("Minus binary op visit")
        return visitChildren(ctx)
    }

    override fun visitMulti(ctx: WACCParser.MultiContext) : Identifier? {
        println("Multiply binary op visit")
        return visitChildren(ctx)
    }

    override fun visitDiv(ctx: WACCParser.DivContext) : Identifier? {
        println("Divide binary op visit")
        return visitChildren(ctx)
    }

    override fun visitMod(ctx: WACCParser.ModContext) : Identifier? {
        println("Modulo binary op visit")
        return visitChildren(ctx)
    }

    override fun visitGt(ctx: WACCParser.GtContext) : Identifier? {
        println("Greater than binary op visit")
        return visitChildren(ctx)
    }

    override fun visitGte(ctx: WACCParser.GteContext) : Identifier? {
        println("Greater than or equal binary op visit")
        return visitChildren(ctx)
    }

    override fun visitLt(ctx: WACCParser.LtContext) : Identifier? {
        println("Less than binary op visit")
        return visitChildren(ctx)
    }

    override fun visitLte(ctx: WACCParser.LteContext) : Identifier? {
        println("Less than or equal binary op visit")
        return visitChildren(ctx)
    }

    override fun visitEquiv(ctx: WACCParser.EquivContext) : Identifier? {
        println("Equivalent binary op visit")
        return visitChildren(ctx)
    }

    override fun visitNotequiv(ctx: WACCParser.NotequivContext) : Identifier? {
        println("Not equivalent binary op visit")
        return visitChildren(ctx)
    }

    override fun visitAnd(ctx: WACCParser.AndContext) : Identifier? {
        println("And binary op visit")
        return visitChildren(ctx)
    }

    override fun visitOr(ctx: WACCParser.OrContext) : Identifier? {
        println("Or binary op visit")
        return visitChildren(ctx)
    }

    override fun visitIntLiteral(ctx: WACCParser.IntLiteralContext): Identifier? {
        println("Expr::IntLiteral visit")
        return visitChildren(ctx)
    }

    override fun visitBoolLiteral(ctx: WACCParser.BoolLiteralContext): Identifier? {
        println("Expr::BoolLiteral visit")
        return visitChildren(ctx)
    }

    override fun visitCharLiteral(ctx: WACCParser.CharLiteralContext): Identifier? {
        println("Expr::CharLiteral visit")
        val chr = ctx.CHAR_LITERAL().symbol.text
        val chrAST = CharLiteral(chr)
        val astValid = chrAST.valid
        if(!chrAST.valid){
            System.err.println(ctx.CHAR_LITERAL().symbol.line.toString() + "ERROROROROOROO")
        }
        valid = astValid && valid
        return chrAST
    }

    override fun visitStringLiteral(ctx: WACCParser.StringLiteralContext): Identifier? {
        println("Expr::StringLiteral visit")
        return visitChildren(ctx)
    }

    override fun visitArrayLiteral(ctx: WACCParser.ArrayLiteralContext): Identifier? {
        println("Expr::ArrayLiteral visit")
        return visitChildren(ctx)
    }

    override fun visitIdentifier(ctx: WACCParser.IdentifierContext): Identifier? {
        println("Expr::Identifier visit")
        return visitChildren(ctx)
    }

    override fun visitArrayElem(ctx: WACCParser.ArrayElemContext): Identifier? {
        println("Expr::ArrayElem visit")
        return visitChildren(ctx)
    }

    override fun visitUnaryOp(ctx: WACCParser.UnaryOpContext): Identifier? {
        println("Expr::UnaryOp visit")
        return visitChildren(ctx)
    }

    override fun visitParens(ctx: WACCParser.ParensContext): Identifier? {
        println("Expr::Parens visit")
        return visitChildren(ctx)
    }
}