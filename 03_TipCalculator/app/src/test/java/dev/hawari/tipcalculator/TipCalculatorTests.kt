package dev.hawari.tipcalculator

import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.NumberFormat

class TipCalculatorTests {

    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.0
        val tipPercent = 20.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(2.0)
        val actualTipNoRoundUp = calculateTip(amount, tipPercent, false)
        assertEquals(expectedTip, actualTipNoRoundUp)

        val actualTipWithRoundUp = calculateTip(amount, tipPercent, true)
        assertEquals(expectedTip, actualTipWithRoundUp)
    }

    @Test
    fun calculateTip_15PercentRoundup() {
        val amount = 25.0
        val tipPercent = 15.0
        val expectedTipNoRoundUp = NumberFormat.getCurrencyInstance().format(3.75)
        val actualTipNoRoundUp = calculateTip(amount, tipPercent, false)
        assertEquals(expectedTipNoRoundUp, actualTipNoRoundUp)

        val expectedTipWithRoundUp = NumberFormat.getCurrencyInstance().format(4)
        val actualTipWithRoundUp = calculateTip(amount, tipPercent, true)
        assertEquals(expectedTipWithRoundUp, actualTipWithRoundUp)
    }
}