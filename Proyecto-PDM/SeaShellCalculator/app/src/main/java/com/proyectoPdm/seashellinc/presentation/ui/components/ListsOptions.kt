package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.presentation.navigation.BuyPremiumScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.ErrorScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassPersonalScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.Marigold
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Composable
fun ListsOptions(
    isLoggedUser: Boolean,
    currentUser: UserEntity?,
    navController: NavController,
    errorViewModel: ErrorViewModel,
    screen: String,
    screenPremium: String,
) {

    Row {

        button(onClick =  {
            navController.navigate(MolarMassScreenSerializable(false, true, screen))
        }, "Masas molares", false)

        Spacer(modifier = Modifier.width(10.dp))

        button(onClick =  {
            if (isLoggedUser) {
                if (currentUser?.user?.isPremium == true) {
                    navController.navigate(
                        MolarMassPersonalScreenSerializable(false, true, screen)
                    )
                } else {
                    navController.navigate(
                        BuyPremiumScreenSerializable(screenPremium)
                    )
                }
            } else {
                errorViewModel.setError("Necesitas estar autenticado/a para usar las funciones Premium. Por favor, inicia sesion en tu cuenta de SeaShellCalculator o registrate.")
                navController.navigate(ErrorScreenSerializable)
            }
        }, "Lista personal", true)
    }
}

@Composable
fun button(
    onClick : () -> Unit,
    text : String,
    isPremium : Boolean
) {

    val modifier = if (isPremium) {
        Modifier
            .width(140.dp).height(45.dp)
            .border(3.7.dp, Marigold, RoundedCornerShape(5.dp))
    } else {
        Modifier.width(140.dp).height(45.dp)
    }

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(MainBlue),
        shape = RoundedCornerShape(5.dp),
        enabled = true,
        modifier = modifier
    ) {
        Text(
            text,
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            color = Buff,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}