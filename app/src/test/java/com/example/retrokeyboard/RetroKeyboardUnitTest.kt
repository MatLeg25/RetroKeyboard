package com.example.retrokeyboard

import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.Key
import com.example.retrokeyboard.models.RetroKeyboard
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before


class RetroKeyboardUnitTest {

    private lateinit var keyboard: RetroKeyboard

    @Before
    fun setUp() {
        keyboard = RetroKeyboard(initMode = KeyboardMode.SENTENCE_CASE)
    }

    @Test
    fun `getNumber return number assigned to given key`() {
        val key = Key(
            symbol = '7',
            chars = listOf('a','b','c','d')
        )
        val numChar = keyboard.getNumber(key)
        assertEquals('7', numChar)
    }

    @Test
    fun `getNumber return null for symbol that is not a number`() {
        val key = Key(
            symbol = '&',
            chars = listOf('a','b','c','d')
        )
        val numChar = keyboard.getNumber(key)
        assertEquals(null, numChar)
    }

    @Test
    fun `change keyboard mode from init mode works`() {
        val modeSentenceCase = keyboard.mode
        keyboard.updateCharCase()
        val modeNext = keyboard.mode
        assertNotEquals(modeSentenceCase, modeNext)
    }

    @Test
    fun `getChars return chars map`() {
        assertNotEquals(keyboard.getChars(), emptyMap<Int, List<Key>>())
    }

    @Test
    fun `getNextChar return correct char`() {
        val key = Key(
            symbol = '&',
            chars = listOf('a','b','c','d')
        )
        val nextChar = keyboard.getNextChar(key, 'b') {}
        assertEquals('c', nextChar?.lowercaseChar())
    }

    @Test
    fun `hash sign change keyboard mode`() {
        val key = Key(
            symbol = '#',
            chars = listOf('a','b','c','d')
        )
        val mode1 = keyboard.mode
        keyboard.getNextChar(key, 'F') {}
        val mode2 = keyboard.mode
        assertNotEquals(mode1, mode2)
    }

    @Test
    fun `getFormattedSymbol return correctly formated symbol`() {
        val k = RetroKeyboard(initMode = KeyboardMode.UPPERCASE)
        val key = Key(
            symbol = 'a',
            chars = listOf('a','b','c','d')
        )
        val formattedSymbol = k.getFormattedSymbol(key)
        assertEquals('A', formattedSymbol)
    }

    @Test
    fun `getFormattedChars return correctly formated chars list`() {
        val k = RetroKeyboard(initMode = KeyboardMode.UPPERCASE)
        val key = Key(
            symbol = 'a',
            chars = listOf('a','b','c','d')
        )
        val formattedChars = k.getFormattedChars(key)
        assertEquals(listOf('A','B','C','D'), formattedChars)
    }

}