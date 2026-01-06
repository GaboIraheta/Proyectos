package com.proyectoPdm.seashellinc.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import com.proyectoPdm.seashellinc.data.model.element.ElementCategory

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Background: Color = Color(0xFFEBDDC5)
val MainBlue: Color = Color(0xFF2E4365)
val LightDarkBlue: Color = Color(0xFF3E5271)
val LightBlue: Color = Color(0xFFD9D9D9)
val DarkBlue: Color = Color(0xFF2A3445)
val CitrineBrown: Color = Color(0xFF8A3B08)
val Buff: Color = Color(0xFFF3D58D)
val Marigold: Color = Color(0xFFE59D2C)

fun getCategoryColor(category: ElementCategory?): Color {
    return when (category) {
        ElementCategory.ALKALI_METAL -> Color(0xFF739977)         // Verde suave
        ElementCategory.ALKALINE_EARTH_METAL -> Color(0xFF832222)  // Rojo oscuro
        ElementCategory.TRANSITION_METAL -> Color(0xFF9146AA)      // Púrpura
        ElementCategory.POST_TRANSITION_METAL -> Color(0xFF3A8C49) // Gris
        ElementCategory.METALLOID -> Color(0xFFF8AA3F)             // Naranja
        ElementCategory.NONMETAL -> Color(0xFF4D92CC)              // Azul
        ElementCategory.NOBLE_GAS -> Color(0xFFB44D4D)             // Rojo rosado
        ElementCategory.LANTHANIDE -> Color(0xFF4D3F9D)            // Azul lavanda
        ElementCategory.ACTINIDE -> Color(0xFFA27536)              // Marrón mostaza
        ElementCategory.UNKNOWN -> Color.Gray
        null -> TODO()
    }
}