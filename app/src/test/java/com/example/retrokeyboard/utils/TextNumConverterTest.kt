package com.example.retrokeyboard.utils

import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.RetroKeyboard
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TextNumConverterTest {

    private lateinit var keyboard: RetroKeyboard
    private lateinit var converter: TextNumConverter

    @Before
    fun setUp() {
        keyboard = RetroKeyboard(initMode = KeyboardMode.SENTENCE_CASE)
        converter = TextNumConverter()
    }

    @Test
    fun `textToNumbers return text converted to numbers`() {
        val input = "Hello world"
        val expected = "44 33 555 555 666 0 9 666 777 555 3"
        val result = converter.textToNumbers(keyboard, input)
        assertEquals(expected, result)
    }

    @Test
    fun `textToNumbers return text converted to numbers with special characters`() {
        val input = "Hello world!"
        val expected = "44 33 555 555 666 0 9 666 777 555 3 !"
        val result = converter.textToNumbers(keyboard, input)
        assertEquals(expected, result)
    }
}