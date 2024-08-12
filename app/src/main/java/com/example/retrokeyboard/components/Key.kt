package com.example.retrokeyboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.extensions.formatText
import com.example.retrokeyboard.models.Key

@Composable
@Preview
fun Key(
    modifier: Modifier = Modifier,
    key: Key = Key('2', listOf('a','b','c')),
    selectedChar: Char? = 'c',
    keyboardMode: KeyboardMode = KeyboardMode.SENTENCE_CASE,
    onClick: suspend () -> Unit = {},
    onLongClick: suspend () -> Unit = {}
) {
    ButtonLongClick(
        modifier = modifier,
        onClick = { onClick() },
        onLongClick = { onLongClick() },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = key.symbol.toString(),
                color =
                    if (selectedChar == key.symbol) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.inversePrimary,
                )
            Chars(chars = key.chars, selectedChar = selectedChar, keyboardMode = keyboardMode)
        }
    }
}

@Composable
fun Chars(
    chars: List<Char>,
    selectedChar: Char?,
    keyboardMode: KeyboardMode
) {
    val maxChars = 5

    if (selectedChar !in chars) {
        KeyChars(text = chars.take(maxChars), keyboardMode = keyboardMode, isSelected = false)
    } else {
        val selectedCharIndex = chars.indexOf(selectedChar)
        val beforeSelected = chars.subList(0, selectedCharIndex)
        val afterSelected = chars.subList(selectedCharIndex+1, chars.size)

        var beforeSelectedTake = 2
        var afterSelectedTake = 2

        //adjust to display max 5 chars
        if (selectedChar != null) {
            if (beforeSelected.size < beforeSelectedTake) {
                afterSelectedTake += beforeSelectedTake - beforeSelected.size
            }
            if (afterSelected.size < afterSelectedTake) {
                beforeSelectedTake += afterSelectedTake - afterSelected.size
            }
        }

        Row() {
            KeyChars(text = beforeSelected.takeLast(beforeSelectedTake), keyboardMode = keyboardMode, isSelected = false)
            if (selectedChar != null) {
                KeyChars(text = listOf(selectedChar), keyboardMode = keyboardMode, isSelected = true)
            }
            KeyChars(text = afterSelected.take(afterSelectedTake), keyboardMode = keyboardMode, isSelected = false)
        }
    }
}

@Composable
fun KeyChars(
    text: List<Char>,
    keyboardMode: KeyboardMode,
    isSelected: Boolean = false
) {
    Text(
        text = text.joinToString("").formatText(keyboardMode),
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary,
        maxLines = 1,
        overflow = TextOverflow.Clip
    )
}