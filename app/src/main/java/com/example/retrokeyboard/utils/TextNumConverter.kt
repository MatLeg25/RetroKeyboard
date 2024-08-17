package com.example.retrokeyboard.utils

import com.example.retrokeyboard.KeyboardContract
import com.example.retrokeyboard.models.Key
import com.example.retrokeyboard.models.RetroKeyboard
import kotlin.jvm.Throws


/**
 * Text to number sequence converter. Converter works only with base chars, special characters are not included.
 * Letter case is not respected - output is always in lowercase.
 * [textToNum] - converts given text to number sequence, corresponding to case when user typing text with [RetroKeyboard]
 * [numToText] - converts back given number sequence to text
 */
object TextNumConverter {

    fun textToNum(keyboard: KeyboardContract, text: String): String {
        var output = ""

        //get key and assigned chars in lowercase
        val keys = keyboard.getKeypadRows().values.flatten()

        text.forEachIndexed { index, char ->
            val isLastChar = (index < text.length - 1)
            output += getNumFromChar(keys, char.lowercaseChar()) + if (isLastChar) " " else ""
        }

        return output
    }

    @Throws(IllegalArgumentException::class)
    fun numToText(keyboard: KeyboardContract, num: String): String {
        var output = ""

        if (num.isEmpty()) return ""

        //get key and assigned chars in lowercase
        val keys = keyboard.getKeypadRows().values.flatten()
        val numbers = num.split(' ')

        val inputValidation = validateNumToTextInput(numbers)
        if (!inputValidation) {
            throw IllegalArgumentException("Incorrect data input!")
        }

        numbers.forEachIndexed { index, n ->
            output += getCharFromNum(keys, n)
        }

        return output
    }

    private fun getNumFromChar(keys: List<Key>, char: Char): String {
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

    private fun getCharFromNum(keys: List<Key>, num: String): Char {
        if (num == "0") return ' '
        keys.forEach { key ->
            if (num.contains(key.symbol)) {
                val index = num.length
                return key.chars.getOrElse(index - 1) { num.first() }
            }
        }
        return num.first()
    }


    /**
     * Validate if items of [numbers] collection are build by single number
     */
    private fun validateNumToTextInput(numbers: List<String>): Boolean {
        numbers.forEach { num ->
            val firstChar = num.first()
            val firstCharCount = num.count {it == firstChar }
            if (firstCharCount != num.length) return false
        }
        return true
    }

}