enum class Sign() {
    MORE,
    LESS,
    EQUALS,
    NOT_EQ,
    MORE_EQ,
    LESS_EQ,
}

data class Condition(val arg: Int, val type: Sign) {

    fun compare(value: Int) = when (type) {
        Sign.MORE -> arg < value
        Sign.LESS -> arg > value
        Sign.EQUALS -> arg == value
        Sign.NOT_EQ -> arg != value
        Sign.MORE_EQ -> arg <= value
        Sign.LESS_EQ -> arg >= value
    }
}