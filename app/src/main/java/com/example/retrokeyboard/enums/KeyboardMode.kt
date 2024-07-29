package com.example.retrokeyboard.enums

enum class KeyboardMode(val value: Int) {
    SENTENCE_CASE(0), LOWERCASE(1), UPPERCASE(2), NUMBER(3);

    companion object {
        fun getNextMode(value: Int): KeyboardMode {
            return entries.getOrNull(value + 1) ?: SENTENCE_CASE
        }
    }
}