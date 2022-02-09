package stat

import symbols.Char
import symbols.Int

class Read(val lhs: AssignLhs) : Stat() {
    init {
        if (!(lhs.type() == Int || lhs.type() == Char)) {
            System.err.println("Expected type Int or Char, got " + lhs.type())
            valid = false
        }
    }
}