package com.example.retrokeyboard

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.Key
import com.example.retrokeyboard.models.RetroKeyboard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                                selectedChar = selectedChar
                            ) {
                                selectedChar = keyboard.getNextChar(it, selectedChar) { kMode ->
                                    keyboardMode = kMode
                                }
                                delay(Config.INPUT_ACTIVE_MS)
                                if (selectedChar != null) {
                                    text += selectedChar
                                    selectedChar = null
                                }
                            }
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
    onClick: suspend () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    Button(
        modifier = modifier,
        onClick = {
            scope.launch {
                onClick()
            }
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = key.symbol.toString(),
                color = if (selectedChar == key.symbol) Color.Blue else Color.White
            )
            Chars(chars = key.chars, selectedChar = selectedChar)
        }
    }
}

@Composable
fun Chars(modifier: Modifier = Modifier, chars: List<Char>, selectedChar: Char?) {
    if (selectedChar !in chars) Text(text = chars.joinToString())
    else {
        val selectedCharIndex = chars.indexOf(selectedChar)
        val beforeSelected = chars.subList(0, selectedCharIndex)
        val afterSelected = chars.subList(selectedCharIndex+1, chars.size)
        Row {
            Text(text = beforeSelected.joinToString(""))
            Text(
                text = selectedChar.toString(),
                color = Color.Blue
            )
            Text(text = afterSelected.joinToString())
        }
    }
}