package com.example.retrokeyboard.utils

import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.RetroKeyboard
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class TextNumConverterTest {

    private lateinit var keyboard: RetroKeyboard
    private var converter: TextNumConverter = TextNumConverter

    @Before
    fun setUp() {
        keyboard = RetroKeyboard(initMode = KeyboardMode.SENTENCE_CASE)
    }

    @Test
    fun `textToNum return text converted to numbers`() {
        val input = "Hello world"
        val expected = "44 33 555 555 666 0 9 666 777 555 3"
        val result = converter.textToNum(keyboard, input)
        assertEquals(expected, result)
    }

    @Test
    fun `textToNum return text converted to numbers with special character`() {
        val input = "Hello world!"
        val expected = "44 33 555 555 666 0 9 666 777 555 3 !"
        val result = converter.textToNum(keyboard, input)
        assertEquals(expected, result)
    }

    @Test
    fun `numToText return text from given numbers sequence`() {
        val input = "44 33 555 555 666 0 9 666 777 555 3"
        val expected = "hello world"
        val result = converter.numToText(keyboard, input)
        assertEquals(expected, result)
    }

    @Test
    fun `numToText return text from given numbers sequence with special character`() {
        val input = "44 33 555 555 666 0 9 666 777 555 3 !"
        val expected = "hello world!"
        val result = converter.numToText(keyboard, input)
        assertEquals(expected, result)
    }

    @Test
    fun `numToText return IllegalArgumentException for incorrect input data`() {
        val input = "11 12 3"
        assertThrows(IllegalArgumentException::class.java) { converter.numToText(keyboard, input) }
    }

}