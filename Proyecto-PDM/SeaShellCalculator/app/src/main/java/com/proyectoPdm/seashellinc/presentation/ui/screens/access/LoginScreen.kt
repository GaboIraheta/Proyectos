package com.proyectoPdm.seashellinc.presentation.ui.screens.access

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyectoPdm.seashellinc.R
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.presentation.navigation.LoginScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MainScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.RegisterScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppTextField
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val context = LocalContext.current

    val isLoading by userViewModel.isLoading.collectAsState()
    val successMessage by userViewModel.successMessage.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()
    val email by userViewModel.email.collectAsState()
    val password by userViewModel.password.collectAsState()
    val accessSuccess by userViewModel.accessSuccess.collectAsState()

//    val isSentEmail by userViewModel.isSentEmail.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            userViewModel.setEmail("")
            userViewModel.setPassword("")
        }
    }

    LaunchedEffect(accessSuccess) {
        if (accessSuccess) {

            userViewModel.resetAccessSuccessState()
            userViewModel.clearSuccessOrErrorMessage()

            navController.navigate(MainScreenSerializable) {
                popUpTo(LoginScreenSerializable) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(successMessage) {
        if (successMessage.isNotEmpty()) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        }
        userViewModel.clearSuccessOrErrorMessage()
//        if (!isSentEmail) {
//            if (successMessage.isNotEmpty()) {
//                Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
//            }
//            userViewModel.clearSuccessOrErrorMessage()
//        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(MainBlue)
                ) {
                    LogoComponent(Modifier.size(60.dp), 1f)
                    Text(
                        "Iniciar Sesión",
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
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.seashelllogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.BottomEnd)
                    .offset(y = (-350).dp),
                alpha = 0.2f
            )
        }
        Column(Modifier.padding(innerPadding)) {
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier.height(70.dp)) {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp) {
                    navController.popBackStack()
                }
            }

        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Background)
                            .padding(30.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = MainBlue)
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = MainBlue,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    AppTextField(
                        email,
                        onValueChange = { newText -> userViewModel.setEmail(newText) },
                        "Correo"
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = MainBlue,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    AppTextField(
                        password,
                        onValueChange = { newText -> userViewModel.setPassword(newText) },
                        "Contraseña",
                        true
                    )
                }
                Spacer(Modifier.height(20.dp))
//                Text(
//                    "¿Olvido la contraseña?",
//                    textAlign = TextAlign.End,
//                    modifier = Modifier.width(330.dp).clickable {
//                        userViewModel.requestRecoveryPassword(email)
//                    },
//                    color = MainBlue,
//                    fontFamily = MontserratFontFamily
//                )
//                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {

                        val loginRequest = UserLoginRequest(
                            email,
                            password
                        )

                        userViewModel.login(loginRequest)
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    modifier = Modifier.border(1.dp, MainBlue, RoundedCornerShape(5.dp)).width(300.dp)
                ) {
                    Text(
                        "Iniciar sesion",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(5.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 25.dp)
                )
            }

//            if (isSentEmail) {
//                Text(
//                    text = successMessage,
//                    color = Color.Green,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(start = 25.dp)
//                )
//            }

            Spacer(Modifier.height(20.dp))
            Row {
                Text("No tienes una cuenta? ", fontFamily = MontserratFontFamily, color = MainBlue)
                Text(
                    "Registrate",
                    modifier = Modifier.clickable {
                        navController.navigate(RegisterScreenSerializable)
                    },
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                    ),
                    color = MainBlue
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview(){
//    val navController = rememberNavController()
//    LoginScreen(navController)
//}