package com.example.retrokeyboard

import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.Key

interface KeyboardContract {
    val mode: KeyboardMode
    fun getKeypadRows(): Map<Int, List<Key>>
    fun getNextChar(key: Key, char: Char?, onModeChanged: (keyboardMode: KeyboardMode) -> Unit): Char?
    fun getNumber(key: Key): Char?
    fun getFormattedSymbol(key: Key): Char
    fun getFormattedChars(key: Key): List<Char>
    fun updateCharCase()
}