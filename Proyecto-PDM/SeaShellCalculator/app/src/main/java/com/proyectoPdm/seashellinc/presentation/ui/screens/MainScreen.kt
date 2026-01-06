package com.proyectoPdm.seashellinc.presentation.ui.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.R
import com.proyectoPdm.seashellinc.presentation.navigation.BalEquationScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.ChemicalUnitsScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.LoginScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PeriodicTableScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PhysicalUnitsScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.proyectoPdm.seashellinc.billing.BillingViewModel
import com.proyectoPdm.seashellinc.billing.PurchaseStatus
import com.proyectoPdm.seashellinc.presentation.navigation.BuyPremiumScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.ErrorScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    errorViewModel : ErrorViewModel
) {

    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val context = LocalContext.current
    val url = "https://sites.google.com/uca.edu.sv/seashellcalculator/"

    val currentUser by userViewModel.currentUser.collectAsState()

    val isLoggedUser by userViewModel.isLoggedUser.collectAsState()
    val successMessage by userViewModel.successMessage.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()

    LaunchedEffect(successMessage) {
        if (successMessage.isNotEmpty()) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        }
        userViewModel.clearSuccessOrErrorMessage()
    }

    LaunchedEffect(errorMessage) {
        if (successMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
        userViewModel.clearSuccessOrErrorMessage()
    }

    fun verifyScreenToChange(screen : String) {
        if (isLoggedUser) {
            if (currentUser?.user?.isPremium == true) {
                navController.navigate(if (screen == "PeriodicTable") PeriodicTableScreenSerializable(false) else BalEquationScreenSerializable(false))
            } else {
                navController.navigate(
                    BuyPremiumScreenSerializable(screen)
                )
            }
        } else {
            errorViewModel.setError("Necesitas estar autenticado/a para usar las funciones Premium. Por favor, inicia sesion en tu cuenta de SeaShellCalculator o registrate.")
            navController.navigate(ErrorScreenSerializable)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(MainBlue)
                ) {
                    LogoComponent(Modifier.size(60.dp), 1f)
                    Text(
                        "Shell's Calculator",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 32.sp),
                        color = Buff
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
                        .height(12.dp)
                        .background(MainBlue)
                ) { }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MainBlue)
                ) {}
            }
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(MainBlue)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Ayuda",
                    tint = Buff,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.seashelllogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 150.dp, y = (225).dp),
                alpha = 0.2f
            )
        }
        if (isLoggedUser) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .zIndex(100f)
            ) {
                IconButton(
                    onClick = {
                        Log.d("IconButtonOnClick", "Se ejecuta el onClick del boton")
                        userViewModel.logout()
                        Log.d("IconButtonOnClick", "En teoria se ejecuto executeLogout()")
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .zIndex(101f),
                    colors = IconButtonDefaults.iconButtonColors(MainBlue)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                        contentDescription = "Cerrar sesión",
                        tint = Buff
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(10.dp))
            Text(
                "Bienvenido a SeaShell\nCalculator",
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(Modifier.height(60.dp))
            AppButton("Unidades físicas\nde concentración", 240.dp, onClick = {
                navController.navigate(
                    PhysicalUnitsScreenSerializable
                )
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Unidades químicas\nde concentración", 240.dp, onClick =  {
                navController.navigate(ChemicalUnitsScreenSerializable)
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Lista de masas\nmolares", 240.dp, onClick =  {
                navController.navigate(MolarMassScreenSerializable(false, false, "Nothing"))
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Balanceador de\necuaciones químicas", 240.dp, isPremium = true, onClick =  {
                verifyScreenToChange("BalEquation")
            })
            Spacer(Modifier.height(20.dp))
            AppButton("Tabla Periódica", 240.dp, isPremium = true, onClick =  {
                verifyScreenToChange("PeriodicTable")
            })
            Spacer(Modifier.height(50.dp))

            if(isLoggedUser) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(MainBlue)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "",
                            tint = Buff,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        currentUser?.user?.username ?: "Usuario",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown,
                        fontSize = 20.sp
                    )
                }
            } else {
                Button(
                    onClick = { navController.navigate(LoginScreenSerializable) },
                    modifier = Modifier
                        .width(170.dp)
                        .border(1.dp, MainBlue, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(
                        "Iniciar Sesión",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}






