package com.example.retrokeyboard.models

import com.example.retrokeyboard.enums.KeyboardMode

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

    fun getNextChar(key: Key, char: Char?, onModeChanged: (keyboardMode: KeyboardMode) -> Unit): Char? {
        if (key.symbol == '#') {
            setNextMode()
            onModeChanged(mode)
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