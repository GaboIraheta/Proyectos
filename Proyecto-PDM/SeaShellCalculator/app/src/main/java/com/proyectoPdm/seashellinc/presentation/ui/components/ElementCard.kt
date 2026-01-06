package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.data.model.element.Element
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import com.proyectoPdm.seashellinc.presentation.ui.theme.getCategoryColor

@Composable
fun ElementCard(element: Element, onClick: (Element) -> Unit) {
    val backgroundColor = getCategoryColor(element.category)
    Card(
        modifier = Modifier
            .size(75.dp)
            //.padding(4.dp)
            .clickable { onClick(element) }
            .border(0.5.dp, Color.Black, RoundedCornerShape(11.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = element.symbol,
                fontFamily = MontserratFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = element.atomicNumber.toString(),
                fontFamily = MontserratFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}