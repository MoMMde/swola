package dev.maerkle.swola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@Composable
fun SwolaTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    Column(modifier = modifier) {
        Text(
            text = label,
            color = SwolaColors.BRIGHT_CREME_WHITE,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                if (keyboardOptions.keyboardType == KeyboardType.Number && it.text.isDigitsOnly()) {
                    textFieldValue = it
                    onValueChange(it.text)
                }
            },
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif, // Arial is a sans-serif font
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            ),
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSwolaTextInputField() {
    Column(Modifier.padding(16.dp)) {
        SwolaTextInputField(
            value = "192.168.0.255",
            onValueChange = { /* Handle value change */ },
            label = "Mac Address"
        )
    }
}