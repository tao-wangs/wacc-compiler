package symbols

abstract class Literal<T>(t : Type) : Expr(t) {
    protected var value : T? = null
        get() {
            return value
        }
}