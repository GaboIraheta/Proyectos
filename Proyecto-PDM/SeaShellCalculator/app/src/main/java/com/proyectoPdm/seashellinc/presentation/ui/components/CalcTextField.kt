package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.data.model.user.Info
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcTextField(
    input: String,
    onValueChange: (String) -> Unit,
    label: String,
    enable: Boolean,
    info: Info? = null,
){
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var textFieldValueState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = input))
    }

    LaunchedEffect(input) {
        if (input != textFieldValueState.text){
            textFieldValueState = textFieldValueState.copy(text = input)
        }
    }

    Column (
        modifier = Modifier.fillMaxWidth(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = DarkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(7.dp))

        if (info != null) {
            ListsOptions(
                info.isLoggedUser,
                info.currentUser,
                info.navController,
                info.errorViewModel,
                info.screen,
                info.screenPremium
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        TextField(
            value = textFieldValueState,
            onValueChange = { newValue ->
                textFieldValueState = newValue
                onValueChange(newValue.text)
                coroutineScope.launch { scrollState.scrollTo(scrollState.maxValue) }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background,
                unfocusedContainerColor = Background,
                disabledContainerColor = Background,
                focusedIndicatorColor = MainBlue,
                unfocusedIndicatorColor = MainBlue,
                disabledIndicatorColor = MainBlue,
                disabledTextColor = CitrineBrown,

                // esto debería forzar a utilizar negro, en vez de por defecto (que podría ser blanco)
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = TextStyle(
                fontWeight = if (!enable) FontWeight.Bold else FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = enable,
            modifier = Modifier
                .horizontalScroll(scrollState)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            // delay(50)
                            textFieldValueState = textFieldValueState.copy(
                                selection = TextRange(0, textFieldValueState.text.length)
                            )
                        }
                    }
                }
        )
    }
}