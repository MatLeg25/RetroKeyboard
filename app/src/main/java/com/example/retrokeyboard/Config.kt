package com.example.retrokeyboard

object Config {

    val COLUMNS_NUMBER = 3
    val INPUT_ACTIVE_MS = 1000L

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


class RetroKeyboard() {
    private val row1 = listOf(
        Key(symbol = '1', chars = listOf('ć')),
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
        Key(symbol = ' ', chars = listOf('*')),
        Key(symbol = '0', chars = listOf(',')),
        Key(symbol = ' ', chars = listOf('#')),
    )

    fun getChars(): Map<Int, List<Key>> {
        return mapOf(1 to row1, 2 to row2, 3 to row3, 4 to row4)
    }

    fun getNextChar(key: Key, char: Char?): Char {
        val chars = key.chars
        return if (char == null) chars.first()
        else {
            val index = chars.indexOf(char) + 1
            val nextIndex = index.coerceIn(0, chars.size)
            chars.getOrNull(nextIndex) ?: chars.first()
        }
    }
}


data class Key(
    val symbol: Char,
    val chars: List<Char>
)