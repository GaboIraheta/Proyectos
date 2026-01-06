package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import kotlinx.coroutines.launch

@Composable
fun BalancerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    val coroutineScope = rememberCoroutineScope()

    var textFieldValueState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = value))
    }

    LaunchedEffect(value) {
        if (value != textFieldValueState.text){
            textFieldValueState = textFieldValueState.copy(text = value)
        }
    }

    TextField(
        value = textFieldValueState,
        onValueChange = { newValue ->
            textFieldValueState = newValue
            onValueChange(newValue.text)
        },
        placeholder = {
            Text(
                text = label,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFD9D9D9),
            focusedContainerColor = Color(0xFFD9D9D9),
            unfocusedTextColor = MainBlue,
            focusedTextColor = MainBlue,
            focusedIndicatorColor = MainBlue,
            focusedLabelColor = MainBlue,
            cursorColor = MainBlue,
        ),
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(120.dp)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        textFieldValueState = textFieldValueState.copy(
                            selection = TextRange(0, textFieldValueState.text.length)
                        )
                    }
                }
            }
    )
}