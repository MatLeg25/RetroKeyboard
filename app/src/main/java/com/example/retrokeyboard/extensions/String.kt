package com.example.retrokeyboard.extensions

import com.example.retrokeyboard.enums.KeyboardMode
import java.util.Locale

fun String.toSentenceCase(): String {
    return this.split(". ").joinToString(". ") { sentence ->
        sentence.trim()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
}

fun String.formatText(mode: KeyboardMode): String {
    return when(mode) {
        KeyboardMode.SENTENCE_CASE -> this.toSentenceCase()
        KeyboardMode.LOWERCASE -> this.lowercase()
        KeyboardMode.UPPERCASE -> this.uppercase()
        KeyboardMode.NUMBER -> this
    }
}