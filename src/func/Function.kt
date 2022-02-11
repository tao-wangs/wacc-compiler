package func

import stat.Stat
import symbols.Identifier
import symbols.Type
import visitor.SymbolTable

class Function(
    currentTable: SymbolTable,
    val id: String,
    val returnType: Type?,
    val params: ParamList,
    val funcSymbolTable: SymbolTable,
    val body: Stat
) : Type() {
    init {
        val t = currentTable.lookup(id)
        if (t != null) {
            if (!(t is FuncType)) {
                ErrorHandler.printErr(ErrorType.SEMANTIC, "\"$id\" is already defined in this scope")
                Identifier.valid = false
            } else if (returnType != t.returnType) {
                ErrorHandler.printErr(ErrorType.SEMANTIC, "Incompatible return types (expected: ${returnType.toString()} actual: ${t.returnType}")
                Identifier.valid = false
            } else if (params.values.size != t.params.size) {
                ErrorHandler.printErr(ErrorType.SEMANTIC, "Incorrect number of parameters for $id (expected: ${t.params.size} actual: ${params.values.size}")
                Identifier.valid = false
            } else {
                for (i in 0..(params.values.size - 1)) {
                    if (params.values[i].paramType != t.params[i]) {
                        ErrorHandler.printErr(ErrorType.SEMANTIC, "Incompatible type at ${params.values[i]} (expected: ${t.params[i]}, actual: ${params.values[i].paramType})")
                        Identifier.valid = false
                    }
                }
            }
        }
    }
}
