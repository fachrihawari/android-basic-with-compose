@file:OptIn(ExperimentalMaterial3Api::class)

package dev.hawari.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.hawari.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculator()
                }
            }
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    leadingIcon: ImageVector? = null
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        leadingIcon = {  if (leadingIcon != null) Icon(leadingIcon, "Leading Icon") }
    )
}

internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean = false): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun TipCalculator(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.calculate_tip),
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(bottom = 16.dp)
        )
        EditNumberField(
            label = R.string.bill_amount,
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)) {
            Text(stringResource(R.string.round_up_tip))
            Switch(checked = roundUp, onCheckedChange = { roundUp = it })
        }
        Text(
            stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = if (amount > 0.0) Color.Green else Color.Black
            )
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipCalculator()
    }
}