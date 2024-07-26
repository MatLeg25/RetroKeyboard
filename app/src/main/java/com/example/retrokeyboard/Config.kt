package com.example.retrokeyboard

object Config {

    val COLUMNS_NUMBER = 3
    val INPUT_ACTIVE_MS = 1000L
    val KEYBOARD_TEXT_MODE_LABEL = "Abc"
    val KEYBOARD_NUM_MODE_LABEL = "123"

    val KEYS = mapOf(
        1 to listOf('x'),
        2 to listOf('a','b','c'),
        3 to listOf('d','e','f'),
        4 to listOf('g','h','i'),
        5 to listOf('j','k','l'),
        6 to listOf('m','n','o'),
        7 to listOf('p','q','r','s'),
        8 to listOf('t','u','v'),
        9 to listOf('w','x','y','z'),
        11 to listOf('*'),
        12 to listOf(','),
        13 to listOf('#')
    )

}

enum class KeyboardMode(val value: Int) {
    SENTENCE_CASE(0), LOWERCASE(1), UPPERCASE(2), NUMBER(3);

    companion object {
        fun getNextMode(value: Int): KeyboardMode {
            return entries.getOrNull(value + 1) ?: SENTENCE_CASE
        }
    }
}

//todo implement observing for mode changed
class RetroKeyboard() {

     var mode: KeyboardMode = KeyboardMode.LOWERCASE
         private set

    private val row1 = listOf(
        Key(symbol = '1', chars = listOf('@')),
        Key(symbol = '2', chars = listOf('a','b','c')),
        Key(symbol = '3', chars = listOf('d','e','f')),
    )
    private val row2 = listOf(
        Key(symbol = '4', chars = listOf('g','h','i')),
        Key(symbol = '5', chars = listOf('j','k','l')),
        Key(symbol = '6', chars = listOf('m','n','o')),
    )
    private val row3 = listOf(
        Key(symbol = '7', chars = listOf('p','q','r','s')),
        Key(symbol = '8', chars = listOf('t','u','v')),
        Key(symbol = '9', chars = listOf('w','x','y','z')),
    )
    private val row4 = listOf(
        Key(symbol = '*', chars = listOf('*','+','-')),
        Key(symbol = '0', chars = listOf(' ')),
        Key(symbol = '#', chars = listOf('#')),
    )

    fun getChars(): Map<Int, List<Key>> {
        return mapOf(1 to row1, 2 to row2, 3 to row3, 4 to row4)
    }

    fun getNextChar(key: Key, char: Char?): Char? {
        if (key.symbol == '#') {
            setNextMode()
            return null
        }
        if (mode == KeyboardMode.NUMBER) return key.symbol
        val chars = key.chars
        val nextChar = if (char == null) chars.first()
        else {
            val index = chars.indexOf(char.lowercaseChar()) + 1
            val nextIndex = index.coerceIn(0, chars.size)
            //after last char display symbol, then go to first char
            if (nextIndex == chars.size) key.symbol else chars.getOrNull(nextIndex) ?: chars.first()
        }
        return setCharCase(nextChar)
    }

    private fun setCharCase(char: Char): Char {
        return when(mode) {
            KeyboardMode.SENTENCE_CASE -> char.titlecaseChar()
            KeyboardMode.LOWERCASE -> char.lowercaseChar()
            KeyboardMode.UPPERCASE -> char.uppercaseChar()
            KeyboardMode.NUMBER -> char
        }
    }

    private fun setNextMode() {
        mode = KeyboardMode.getNextMode(mode.value)
    }

}


data class Key(
    val symbol: Char,
    val chars: List<Char>
)