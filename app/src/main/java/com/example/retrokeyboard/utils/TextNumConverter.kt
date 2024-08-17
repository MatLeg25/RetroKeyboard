package com.example.retrokeyboard.utils

import com.example.retrokeyboard.models.Key
import com.example.retrokeyboard.models.RetroKeyboard

class TextNumConverter {

    fun textToNumbers(keyboard: RetroKeyboard, text: String): String {
        var output = ""

        //get key and assigned chars in lowercase
        val keys = keyboard.getKeypadRows().values.flatten()

        text.forEachIndexed { index, char ->
            val isLastChar = (index < text.length - 1)
            output += getConvertedChar(keys, char.lowercaseChar()) + if (isLastChar) " " else ""
        }

        return output
    }

    private fun getConvertedChar(keys: List<Key>, char: Char): String {
        if (char == ' ') return "0"
        keys.forEach { key ->
            val index = key.chars.indexOf(char)
            if (index > -1) {
                val number = key.symbol.toString().toIntOrNull()
                return number?.toString()?.repeat(index + 1) ?: char.toString()
            }
        }
        return char.toString()
    }

}