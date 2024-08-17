package com.example.retrokeyboard.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrokeyboard.Config
import com.example.retrokeyboard.KeyboardContract
import com.example.retrokeyboard.R
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.extensions.copyTextToClipboard
import com.example.retrokeyboard.models.RetroKeyboard
import com.example.retrokeyboard.ui.theme.NokiaScreenDark
import com.example.retrokeyboard.ui.theme.NokiaScreenLight
import com.example.retrokeyboard.utils.TextNumConverter
import kotlinx.coroutines.delay

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    keyboard: KeyboardContract = RetroKeyboard()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val keyWidthDp = (screenWidthDp / 3)

    var text by remember { mutableStateOf("") }
    var extraInfoVisible by remember { mutableStateOf(true) }
    var selectedChar: Char? by remember { mutableStateOf(null) }
    var keyboardMode by remember { mutableStateOf(keyboard.mode) }
    var cursorPosition by remember { mutableIntStateOf(text.length) }

    val label = run {
        val keyboardCase = when (keyboardMode) {
            KeyboardMode.SENTENCE_CASE -> Config.KEYBOARD_TEXT_MODE_LABEL.capitalize(Locale.current)
            KeyboardMode.LOWERCASE -> Config.KEYBOARD_TEXT_MODE_LABEL.lowercase()
            KeyboardMode.UPPERCASE -> Config.KEYBOARD_TEXT_MODE_LABEL.uppercase()
            KeyboardMode.NUMBER -> Config.KEYBOARD_NUM_MODE_LABEL
        }
        if (extraInfoVisible) {
            val extraInfo = stringResource(id = R.string.extra_info_cursor_X_text_Y, cursorPosition, text.length)
            keyboardCase + extraInfo
        }
        else keyboardCase
    }

    fun updateSelectedChar() {
        if (selectedChar != null) {
            if (text.isEmpty()) {
                text = selectedChar.toString()
            } else {
                val beforeCursor = text.substring(0, cursorPosition)
                val afterCursor = text.substring(cursorPosition, text.length)
                text = beforeCursor + selectedChar + afterCursor
            }
            keyboard.updateCharCase()
            keyboardMode = keyboard.mode
            cursorPosition++
            selectedChar = null
        }
    }

    var isVisible by remember { mutableStateOf(true) }
    var textWithCursor by remember { mutableStateOf(text) }

    //IMPROVE ME: find better way to deal with empty text (jumping label issue)
    val textColor by animateColorAsState(
        targetValue = if (text.isEmpty()) {
            val typingLetter = selectedChar?.toString() ?: ""
            textWithCursor = "$typingLetter|"
            if (isVisible) NokiaScreenDark else Color.Transparent
        } else NokiaScreenDark,
        label = ""
    )


    LaunchedEffect(Unit) {
        while (true) {
            isVisible = !isVisible
            val typingLetter = selectedChar?.toString() ?: ""
            textWithCursor = if (isVisible) text else text.substring(0, cursorPosition) + typingLetter + "|" + text.substring(cursorPosition, text.length)
            delay(Config.INPUT_ACTIVE_MS/3)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            //Text box
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("TextField"),
                value = textWithCursor,
                onValueChange = { text = it },
                label = {
                    Column {
                        Text(
                            text = label,
                            fontFamily = Config.fontNokia3310,
                            color = NokiaScreenDark
                        )
                        HorizontalDivider(Modifier.height(12.dp))
                    }

                },
                enabled = false,
                textStyle = TextStyle.Default.copy(fontFamily = Config.fontNokia3310, fontSize = 16.sp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = NokiaScreenLight,
                    unfocusedContainerColor = NokiaScreenLight,
                    disabledContainerColor = NokiaScreenLight,
                ),
            )

            //Keyboard support buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.background(Color.DarkGray).border(BorderStroke(0.1.dp, Color.LightGray)),
                    onClick = {
                        text = ""
                        cursorPosition = 0
                    }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear)
                    )
                }
                IconButton(
                    modifier = Modifier.background(Color.DarkGray).border(BorderStroke(0.1.dp, Color.LightGray)),
                    onClick = {
                        if (text.isNotEmpty()) {
                            text = text.substring(0, text.length - 1)
                            cursorPosition = (cursorPosition-1).coerceIn(0, text.length)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.backspace)
                    )
                }
                IconButton(
                    modifier = Modifier.background(Color.Gray).border(BorderStroke(0.1.dp, Color.DarkGray)),
                    onClick = {
                        cursorPosition = (cursorPosition-1).coerceIn(0, text.length)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.move_cursor_left)
                    )
                }
                IconButton(
                    modifier = Modifier.background(Color.Gray).border(BorderStroke(0.1.dp, Color.DarkGray)),
                    onClick = {
                        cursorPosition = (cursorPosition+1).coerceIn(0, text.length)
                })  {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.move_cursor_right),
                    )
                }
                IconButton(
                    modifier = Modifier.background(Color.Gray).border(BorderStroke(0.1.dp, Color.DarkGray)),
                    onClick = {
                        context.copyTextToClipboard(text)
                    }) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = stringResource(id = R.string.copy_to_clipboard)
                    )
                }
                IconButton(
                    modifier = Modifier.background(Color.DarkGray).border(BorderStroke(0.1.dp, Color.LightGray)),
                    onClick = {
                        text = TextNumConverter.textToNum(keyboard, text)
                        cursorPosition = text.length
                    }
                ) {
                    Text(modifier = Modifier.rotate(-10f) ,text = "b⮕22", fontSize = 10.sp, color = Color.LightGray)
                }
                IconButton(
                    modifier = Modifier.background(Color.DarkGray).border(BorderStroke(0.1.dp, Color.LightGray)),
                    onClick = {
                        try {
                            text = TextNumConverter.numToText(keyboard, text)
                            cursorPosition = text.length
                        } catch (e: IllegalArgumentException) {
                            //todo improve error handling - update UI to inform user about invalid input
                            text = "?".repeat(text.length)
                            cursorPosition = text.length
                        }

                    }
                ) {
                    Text(modifier = Modifier.rotate(-10f) ,text = "22⮕b", fontSize = 10.sp, color = Color.LightGray)
                }
            }

            //Keyboard rows
            Column() {
                keyboard.getKeypadRows().forEach { (_, key) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        key.forEach {
                            Key(
                                modifier = Modifier
                                    .width(keyWidthDp)
                                    .padding(12.dp),
                                symbol = keyboard.getFormattedSymbol(it),
                                chars = keyboard.getFormattedChars(it),
                                selectedChar = selectedChar,
                                keyboardMode = keyboardMode,
                                onClick = {
                                    selectedChar = keyboard.getNextChar(it, selectedChar) { kMode ->
                                        keyboardMode = kMode
                                    }
                                    delay(Config.INPUT_ACTIVE_MS)
                                    updateSelectedChar()
                                },
                                onLongClick = {
                                    selectedChar = keyboard.getNumber(it)
                                    updateSelectedChar()
                                }
                            )
                        }
                    }
                }
            }
        }

    }

}