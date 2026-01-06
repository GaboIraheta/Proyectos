package com.proyectoPdm.seashellinc.presentation.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.Marigold
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyectoPdm.seashellinc.billing.BillingViewModel
import com.proyectoPdm.seashellinc.presentation.navigation.BalEquationScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.ErrorScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassPersonalScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PeriodicTableScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable.PeriodicTableScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import android.app.Activity
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.proyectoPdm.seashellinc.billing.PurchaseStatus
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BuyPremiumScreen(
    navController : NavController,
    userViewModel: UserViewModel,
    errorViewModel: ErrorViewModel,
    screen : String,
    billingViewModel: BillingViewModel
) {

    val navigationBarHeigh = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val context = LocalContext.current

    val activity = context as? Activity

    val isLoading by userViewModel.isLoading.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()
    val successMessage by userViewModel.successMessage.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    val isPayPremiumSuccess by userViewModel.isPayPremiumSuccess.collectAsState()
    val isPayPremiumFailure by userViewModel.isPayPremiumFailure.collectAsState()

    val purchaseStatus by billingViewModel.purchaseStatus.collectAsState()

    val isPurchaseLoading = purchaseStatus is PurchaseStatus.Loading

    LaunchedEffect(successMessage) {
        if (successMessage.isNotEmpty() && isPayPremiumSuccess) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        }
        userViewModel.clearSuccessOrErrorMessage()
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            errorViewModel.setError(errorMessage)
            navController.navigate(ErrorScreenSerializable)
        }
    }

    LaunchedEffect(purchaseStatus) {
        when (purchaseStatus) {
            is PurchaseStatus.Success -> {
                billingViewModel.resetStatus()
                userViewModel.setIsPayPremiumSuccess(true)
                if (currentUser?.user?.isPremium == true) {
                    if (screen == "MolarMassPersonal")
                        navController.navigate(
                            MolarMassPersonalScreenSerializable(true, false, "Nothing")
                        )
                    else if (screen == "PeriodicTable")
                        navController.navigate(
                            PeriodicTableScreenSerializable(true)
                        )
                    else if (screen == "BalEquation")
                        navController.navigate(
                            BalEquationScreenSerializable(true)
                        )
                    else {
                        errorViewModel.setError("Error en la carga de la pantalla de funcionalidad premium, sal y vuelve a entrar.")
                        navController.navigate(ErrorScreenSerializable)
                    }
                } else {
                    errorViewModel.setError("No se pudo confirmar el pago de SeaShellCalculator Premium.")
                    navController.navigate(ErrorScreenSerializable)
                }
            }

            is PurchaseStatus.Error -> {
                userViewModel.updatedPremiumStatus(false, true)
                billingViewModel.resetStatus()
                errorViewModel.setError((purchaseStatus as PurchaseStatus.Error).message)
                navController.navigate(ErrorScreenSerializable)
            }

            else -> {}
        }
    }

    val benefitList = listOf(
        "Sin anuncios",
        "Tabla periódica interactiva",
        "Balanceador de ecuaciones",
        "Lista de compuestos ilimitada!"
    )
    val gradientColors = listOf(
        Color(0xFF6881AB),
        DarkBlue
    )
    val gradient = Brush.verticalGradient(
        colors = gradientColors,
        startY = 0.1f,
        endY = Float.POSITIVE_INFINITY
    )
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(gradient)
        )
        Column(Modifier.padding(paddingValues)) {
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier.height(70.dp)) {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp) {
                    navController.popBackStack()
                }
            }
        }
        Spacer(Modifier.height(40.dp))
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = navigationBarHeigh),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoComponent(modifier = Modifier.size(200.dp), 1f)
            if (isLoading /*|| isPurchaseLoading*/) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(30.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            } else {
                Text(
                    "SeaShell Premium\n\n$0.99",
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp
                )
                Text(
                    "\nPago unico",
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(75.dp))
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    benefitList.forEach { item ->
                        Row {
                            Text(
                                "•",
                                modifier = Modifier.padding(end = 8.dp),
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                            Text(
                                item, fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                            Spacer(Modifier.height(50.dp))
                        }
                    }
                }
                Spacer(Modifier.height(70.dp))
                Button(
                    onClick = {
                        if (purchaseStatus is PurchaseStatus.Idle) {
                            billingViewModel.initBillingManager()
                            userViewModel.updatedPremiumStatus(true, false)
                            if (!isPayPremiumFailure) {
                                activity?.let {
                                    billingViewModel.launchPurchase(it, "premium")
                                }
                            }
                        }
                    },
                    enabled = !isPurchaseLoading,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.border(3.7.dp, Marigold, RoundedCornerShape(5.dp))
                ) {
                    Text(
                        "Mejorar a Premium!",
                        color = Color.White,
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}