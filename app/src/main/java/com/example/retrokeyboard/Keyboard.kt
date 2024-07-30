package com.example.retrokeyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.extensions.formatText
import com.example.retrokeyboard.models.Key
import com.example.retrokeyboard.models.RetroKeyboard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    keyboard: KeyboardContract = RetroKeyboard()
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val keyWidthDp = screenWidthDp / 3

    var text by remember { mutableStateOf("") }
    var selectedChar: Char? by remember { mutableStateOf(null) }
    var keyboardMode by remember { mutableStateOf(keyboard.mode) }
    
    val label = when (keyboardMode) {
        KeyboardMode.SENTENCE_CASE -> Config.KEYBOARD_TEXT_MODE_LABEL.capitalize(Locale.current)
        KeyboardMode.LOWERCASE -> Config.KEYBOARD_TEXT_MODE_LABEL.lowercase()
        KeyboardMode.UPPERCASE -> Config.KEYBOARD_TEXT_MODE_LABEL.uppercase()
        KeyboardMode.NUMBER -> Config.KEYBOARD_NUM_MODE_LABEL
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Spacer(modifier = Modifier.size(100.dp))
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(label) }
            )
            Column(Modifier.fillMaxWidth()) {
                keyboard.getChars().forEach { (_, key) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        key.forEach {
                            Key(
                                modifier = Modifier.width(keyWidthDp),
                                key = it,
                                selectedChar = selectedChar,
                                keyboardMode = keyboardMode,
                                onClick = {
                                    selectedChar = keyboard.getNextChar(it, selectedChar) { kMode ->
                                        keyboardMode = kMode
                                    }
                                    delay(Config.INPUT_ACTIVE_MS)
                                    if (selectedChar != null) {
                                        text += selectedChar
                                        selectedChar = null
                                    }
                                },
                                onLongClick = {
                                    selectedChar = keyboard.getNumber(it)
                                    if (selectedChar != null) {
                                        text += selectedChar
                                        selectedChar = null
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

    }

}

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
    modifier: Modifier = Modifier,
    chars: List<Char>,
    selectedChar: Char?,
    keyboardMode: KeyboardMode
) {
    if (selectedChar !in chars) {
        KeyChars(text = chars, keyboardMode = keyboardMode, isSelected = false)
    } else {
        val selectedCharIndex = chars.indexOf(selectedChar)
        val beforeSelected = chars.subList(0, selectedCharIndex)
        val afterSelected = chars.subList(selectedCharIndex+1, chars.size)
        Row {
            KeyChars(text = beforeSelected, keyboardMode = keyboardMode, isSelected = false)
            if (selectedChar != null) {
                KeyChars(text = listOf(selectedChar), keyboardMode = keyboardMode, isSelected = true)
            }
            KeyChars(text = afterSelected, keyboardMode = keyboardMode, isSelected = false)
        }
    }
}

@Composable
fun ButtonLongClick(
    modifier: Modifier = Modifier,
    onClick: suspend () -> Unit = {},
    onLongClick: suspend () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val viewConfiguration = LocalViewConfiguration.current

    LaunchedEffect(interactionSource) {
        var isLongClick = false

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    isLongClick = true
                    onLongClick()
                }
                is PressInteraction.Release -> {
                    if (isLongClick.not()) {
                        onClick()
                    }
                }
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = {},
        interactionSource = interactionSource
    ) {
        content()
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