package com.example.duoswipe.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R
import com.example.duoswipe.ui.theme.BgColor
import com.example.duoswipe.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    textValue: TextFieldValue,
    onValueChange: (newValue: TextFieldValue) -> Unit,
    labelValue: String,
    painterResource: Painter
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        maxLines = 1,
        singleLine = true,
        value = textValue,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = ""
            )
        }
    )
}

/*@Preview
@Composable
fun PrevTextField() {
    TextField("nasuh", painterResource(id = R.drawable.baseline_alternate_email_24),"nasuh")
}*/

