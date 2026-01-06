package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun SyntaxHighlightedFormula(
    formula: String,
    errorRange: IntRange,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val annotatedString = buildAnnotatedString {
            // Parte antes del error
            append(formula.substring(0, errorRange.first))

            // Parte del error, resaltada
            withStyle(SpanStyle(background = Color.Red.copy(alpha = 0.3f))) { // Fondo rojo translúcido
                append(formula.substring(errorRange.first, errorRange.last))
            }

            // Parte después del error
            append(formula.substring(errorRange.last, formula.length))
        }

        // Mostrar la fórmula con el resaltado
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium // Estilo similar al de la ecuación balanceada
        )
        // Mostrar el mensaje de error debajo
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}