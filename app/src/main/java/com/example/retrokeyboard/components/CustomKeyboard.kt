package com.example.retrokeyboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.example.retrokeyboard.Config
import com.example.retrokeyboard.KeyboardContract
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.RetroKeyboard
import kotlinx.coroutines.delay

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
    var cursorPosition by remember { mutableIntStateOf(text.length) }

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
                label = { Text(label) },
                enabled = false
            )
            Row {
                Button(onClick = {
                    if (text.isNotEmpty()) {
                        text = text.substring(0, text.length - 1)
                        cursorPosition = (cursorPosition-1).coerceIn(0, text.length)
                    }
                }) {
                    Text(text = "C")
                }
                Button(onClick = {
                    cursorPosition = (cursorPosition-1).coerceIn(0, text.length)
                }) {
                    Text(text = "<")
                }
                Button(onClick = {
                    cursorPosition = (cursorPosition+1).coerceIn(0, text.length)
                }) {
                    Text(text = ">")
                }
            }
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
                                        if (text.isEmpty()) {
                                            text = selectedChar.toString()
                                        } else {
                                            val beforeCursor = text.substring(0, cursorPosition)
                                            val afterCursor = text.substring(cursorPosition, text.length)
                                            text = beforeCursor + selectedChar + afterCursor
                                        }
                                        cursorPosition++
                                        selectedChar = null
                                    }
                                },
                                onLongClick = {
                                    selectedChar = keyboard.getNumber(it)
                                    if (selectedChar != null) {
                                        text += selectedChar
                                        cursorPosition = text.length + 1
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