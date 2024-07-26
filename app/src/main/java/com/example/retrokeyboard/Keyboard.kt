package com.example.retrokeyboard

import android.widget.Toast
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

@Composable
fun CustomKeyboard(modifier: Modifier = Modifier) {
    val keybaord = RetroKeyboard()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val keyWidthDp = screenWidthDp / 3

    var text by remember { mutableStateOf("") }
    var selectedChar: Char? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Spacer(modifier = Modifier.size(100.dp))
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") }
            )
            Column(Modifier.fillMaxWidth()) {
                keybaord.getChars().forEach { (_, key) ->
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
                                selectedChar = keybaord.getNextChar(it, selectedChar)
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
            Text(text = key.symbol.toString())
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