package dev.hawari.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import dev.hawari.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalculator()
            }
        }

        val defaultTip = NumberFormat.getCurrencyInstance().format(0.0)
        composeTestRule.onNodeWithText("Tip Amount: $defaultTip")
            .assertExists("No node with this text exists")

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")

        composeTestRule.onNodeWithText("Tip Percentage")
            .performTextInput("20")

        val expectedTipRoundUp = NumberFormat.getCurrencyInstance().format(2.0)
        composeTestRule.onNodeWithText("Tip Amount: $expectedTipRoundUp")
            .assertExists("No node with this text exists")
    }
}