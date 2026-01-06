package com.proyectoPdm.seashellinc.presentation.ui.screens.chemicalEquationBalancer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proyectoPdm.seashellinc.presentation.navigation.MainScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.BalancerTextField
import com.proyectoPdm.seashellinc.presentation.ui.components.SyntaxHighlightedFormula
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@Composable
fun EquationBalancerScreen(
    navController: NavController,
    backOfPremium : Boolean,
    viewModel: EquationBalancerViewModel = hiltViewModel()
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = navigationBarHeight),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(17.dp)
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
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(DarkBlue)
                        .padding(paddingValues)
                        .width(18.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(LightDarkBlue)
                        .padding(paddingValues)
                        .width(17.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Box(Modifier.height(200.dp).background(Color.Black).width(8.dp))
                Spacer(Modifier.width(10.dp))
                Box(Modifier.height(300.dp).background(MainBlue).width(8.dp))
                Spacer(Modifier.width(10.dp))
            }
        }

        val scrollState = rememberScrollState()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationBarHeight, top = paddingValues.calculateTopPadding() + 10.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Spacer(Modifier.width(50.dp))
                AppGoBackButton(60.dp){
                    if(backOfPremium) {
                        navController.navigate(MainScreenSerializable)
                        return@AppGoBackButton
                    }
                    navController.popBackStack()
                }
            }

            Spacer(Modifier.height(50.dp))

            //Title Screen
            Row (
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(DarkBlue)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "BALANCEADOR DE ECUACIONES QUÍMICAS",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = CitrineBrown
                )
            }
            Spacer(Modifier.height(60.dp))

            BalancerTextField(
                value = uiState.formulaInput,
                onValueChange = { viewModel.onFormulaInputChange(it) },
                label = "Escribe tu fórmula aquí (ej. Cu(NO3)2 = CuO + NO2 + O2)",
            )
            Spacer(Modifier.height(20.dp))

            AppButton(
                onClick = {
                    viewModel.onBalanceButtonClicked()
                    focusManager.clearFocus()
                },
                enabled = !uiState.isLoading,
                text = if (uiState.isLoading) "Balanceando..." else "Balancear",
                width = 150.dp
            )
            Spacer(Modifier.height(20.dp))

            Box(
                Modifier
                    .fillMaxWidth(0.7f)
                    .border(
                        1.dp, MainBlue,
                        MaterialTheme.shapes.small
                    )
                    .background(
                        LightBlue,
                        MaterialTheme.shapes.small
                    )
                    .padding(16.dp)
            ){
                when {
                    uiState.balancedEquation != null -> {
                        uiState.balancedEquation?.let { (equation, coefficients) ->
                        val annotatedEquation = equation.toAnnotatedString(coefficients)
                            Text(
                                text = annotatedEquation,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    uiState.syntaxErrorRange != null && uiState.errorMessage != null -> {
                        SyntaxHighlightedFormula(
                            formula = uiState.formulaInput,
                            errorRange = uiState.syntaxErrorRange!!,
                            errorMessage = uiState.errorMessage!!
                        )
                    }
                    uiState.errorMessage != null -> {
                        uiState.errorMessage?.let { message ->
                            Text(text = message, color = MaterialTheme.colorScheme.error)
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    uiState.isLoading -> {
                        Text(
                            text = "Calculando...",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }
                    else -> {
                        Text(
                            text = "Introduce una fórmula para balancear",
                            color = MainBlue.copy(alpha = 0.6f),
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}