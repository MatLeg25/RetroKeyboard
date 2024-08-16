package com.example.retrokeyboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.retrokeyboard.components.CustomKeyboard
import com.example.retrokeyboard.enums.KeyboardMode
import com.example.retrokeyboard.models.RetroKeyboard
import com.example.retrokeyboard.ui.theme.RetroKeyboardTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RetroKeyboardInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            RetroKeyboardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CustomKeyboard(
                        keyboard = RetroKeyboard(KeyboardMode.SENTENCE_CASE),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.retrokeyboard", appContext.packageName)
    }


    //Hello world! = 44 33 555 555 666 0 9 666 777 555 3 (18x '*')
    @Test
    fun screen_correctly_display_hello_world_text() {
        composeRule.onNodeWithTag("TextField").assertIsDisplayed()

        composeRule.onNodeWithText("4").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("4").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("3").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("3").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("0").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("9").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("6").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("7").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("7").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("7").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(100)
        composeRule.onNodeWithText("5").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        composeRule.onNodeWithText("3").performClick()
        composeRule.mainClock.advanceTimeBy(1000)

        for (i in 1..18) {
            composeRule.onNodeWithText("*").performClick()
            composeRule.mainClock.advanceTimeBy(100)
        }
        composeRule.mainClock.advanceTimeBy(3000)

        val text = "Hello world!"
        val textWithCursor = "$text|"
        val textUi = composeRule.onNodeWithTag("TextField").fetchSemanticsNode().config[SemanticsProperties.EditableText].text

        val result = (text == textUi || textWithCursor == textUi)
        assertTrue(result)
    }

}