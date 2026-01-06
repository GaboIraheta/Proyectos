package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.R

@Composable
fun LogoComponent(
    modifier: Modifier,
    opacity: Float
){
    Image(
        painter = painterResource(id = R.drawable.seashelllogo),
        contentDescription = "Logo",
        modifier = modifier,
        alpha = opacity,
        contentScale = ContentScale.Fit
    )
}