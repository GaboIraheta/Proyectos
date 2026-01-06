package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyectoPdm.seashellinc.data.model.calculators.CalculationResult
import com.proyectoPdm.seashellinc.data.model.calculators.ToCalculate
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.CalcTextField
import com.proyectoPdm.seashellinc.presentation.ui.components.SelectionMenu
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.PhysicalCalculatorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@Composable
fun MassOverMassCalculator(
    navController: NavController,
    viewModel: PhysicalCalculatorViewModel = hiltViewModel()
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    var selectedOutput by remember { mutableStateOf(ToCalculate.CONCENTRATION) }

    val solute by viewModel.solute.collectAsState()
    val solvent by viewModel.solvent.collectAsState()
    val concentration by viewModel.concentration.collectAsState()
    val calculationResult by viewModel.calculationResult.collectAsState()
    
    LaunchedEffect(solute, solvent, concentration, selectedOutput) {
        when (selectedOutput) {
            ToCalculate.SOLUTE -> viewModel.calculateRequiredSoluteMMVV()
            ToCalculate.SOLVENT -> viewModel.calculateRequiredSolventMMVV()
            ToCalculate.CONCENTRATION -> viewModel.calculateConcentrationPercentageMMVV()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background
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
                Box(Modifier
                    .height(200.dp)
                    .background(Color.Black)
                    .width(8.dp))
                Spacer(Modifier.width(10.dp))
                Box(Modifier
                    .height(300.dp)
                    .background(MainBlue)
                    .width(8.dp))
                Spacer(Modifier.width(10.dp))
            }
        }

        val scrollState = rememberScrollState()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationBarHeight, top = paddingValues.calculateTopPadding())
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(17.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),

            ){
                Spacer(Modifier.width(50.dp))
                AppGoBackButton(60.dp){
                    navController.popBackStack()
                    viewModel.clearAllInputs()
                }
            }

            Spacer(Modifier.height(40.dp))

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
                    text = "PORCENTAJE REFERIDO A LA MASA",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = CitrineBrown
                )
            }
            Spacer(Modifier.height(50.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.65f)
            ){
                Text(
                    text = "Encontrar:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(16.dp))

                SelectionMenu(
                    itemList = ToCalculate.entries.toList(),
                    selectedItem = selectedOutput,
                    onItemSelected = { item ->
                        if (calculationResult is CalculationResult.Error) viewModel.clearAllInputs()
                        selectedOutput = item
                    },
                    itemContent = { item ->
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(40.dp))

            CalcTextField(
                input = if (selectedOutput == ToCalculate.SOLUTE) {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                } else solute,
                onValueChange = { viewModel.onSoluteChange(it) },
                label = "Soluto (g)",
                enable = selectedOutput != ToCalculate.SOLUTE,
                info = null
            )
            Spacer(Modifier.height(20.dp))

            CalcTextField(
                input = if (selectedOutput == ToCalculate.SOLVENT) {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                } else solvent,
                onValueChange = { viewModel.onSolventChange(it) },
                label = "Solvente (g)",
                enable = selectedOutput != ToCalculate.SOLVENT,
                info = null

            )
            Spacer(Modifier.height(20.dp))

            CalcTextField(
                input = if (selectedOutput == ToCalculate.CONCENTRATION) {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                } else concentration,
                onValueChange = { viewModel.onConcentrationChange(it) },
                label = "Concentración (%)",
                enable = selectedOutput != ToCalculate.CONCENTRATION,
                info = null

            )
            Spacer(Modifier.height(40.dp))

            AppButton(
                text = "Limpiar",
                width = 120.dp,
                onClick = { viewModel.clearAllInputs() }
            )
        }
    }
}