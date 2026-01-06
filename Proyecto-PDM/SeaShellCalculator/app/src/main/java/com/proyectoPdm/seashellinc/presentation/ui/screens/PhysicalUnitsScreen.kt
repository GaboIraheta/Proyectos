package com.proyectoPdm.seashellinc.presentation.ui.screens

import com.proyectoPdm.seashellinc.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.navigation.MassOverMassCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MassOverVolumeCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PartsPerMillionCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.VolumeOverVolumeCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.Marigold
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Composable
fun PhysicalUnitsScreen(navController: NavController) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(91.dp)
                        .background(MainBlue)
                ) {
                    Text(
                        "Unidades físicas de\nconcentración", fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold, color = Buff, textAlign = TextAlign.Center,
                        fontSize = 32.sp
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = navigationBarHeight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(LightDarkBlue)
                ) { }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(DarkBlue)
                ) {}
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.seashelllogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-150).dp, y = (225).dp),
                alpha = 0.2f
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(80.dp)) {
                Box(modifier = Modifier
                    .height(70.dp)
                    .width(10.dp)
                    .background(Marigold))
                Spacer(Modifier.width(10.dp))
                Text(
                    "Las unidades físicas definen\nla proporción entre el soluto\ny el solvente.",
                    fontFamily = MontserratFontFamily,
                    fontSize = 20.sp
                )

            }

            Spacer(Modifier.height(80.dp))

            AppButton("Porcentaje referido a la masa", 300.dp, onClick = {
                navController.navigate(MassOverMassCalculatorSerializable)
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Porcentaje referido al volumen", 300.dp, onClick = {
                navController.navigate(VolumeOverVolumeCalculatorSerializable)
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Porcentaje masa-volumen", 300.dp, onClick = {
                navController.navigate(MassOverVolumeCalculatorSerializable)
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Partes por millon", 300.dp, onClick = {
                navController.navigate(PartsPerMillionCalculatorSerializable)
            })
            Spacer(Modifier.height(70.dp))
            AppGoBackButton(80.dp, {
                navController.popBackStack()
            })

        }

    }
}