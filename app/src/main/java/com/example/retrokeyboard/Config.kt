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