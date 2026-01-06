package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molality

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.data.model.user.Info
import com.proyectoPdm.seashellinc.data.model.calculators.CalculationResult
import com.proyectoPdm.seashellinc.presentation.navigation.ChemicalUnitsScreenSerializable
import androidx.navigation.compose.rememberNavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.CalcTextField
import com.proyectoPdm.seashellinc.presentation.ui.components.SelectionMenu
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molarity.MolarityCalculatorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MolalityCalculator(
    navController: NavController,
    viewModel: MolalityCalculatorViewModel = hiltViewModel(),
    userViewModel: UserViewModel,
    errorViewModel : ErrorViewModel,
    molarMassPersonalViewModel: MolarMassPersonalViewModel,
    molarMassViewModel: MolarMassViewModel
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    var selectedOutput by remember { mutableStateOf("Molalidad") }

    val solute by viewModel.solute.collectAsState()
    val solvent by viewModel.solvent.collectAsState()
    val soluteMolarMass by viewModel.soluteMolarMass.collectAsState()
    val molarity by viewModel.molarity.collectAsState()
    val calculationResult by viewModel.calculationResult.collectAsState()

    val currentUser by userViewModel.currentUser.collectAsState()
    val isLoggedUser by userViewModel.isLoggedUser.collectAsState()

    val molarMassForCalculator by molarMassPersonalViewModel.molarMassForMolalityCalculator.collectAsState()
    val molarMassForCalculatorFromMolarMassList by molarMassViewModel.molarMassForMolalityCalculator.collectAsState()

    val info = Info(
        isLoggedUser,
        currentUser,
        navController,
        errorViewModel,
        "Molality"
    )

    LaunchedEffect(molarMassForCalculatorFromMolarMassList) {
        if (molarMassForCalculatorFromMolarMassList.isNotEmpty()) {
            viewModel.onSoluteMolarMassChange(molarMassForCalculatorFromMolarMassList)
        }
    }

    LaunchedEffect(molarMassForCalculator) {
        if (molarMassForCalculator.isNotEmpty()) {
            viewModel.onSoluteMolarMassChange(molarMassForCalculator)
        }
    }

    LaunchedEffect(solute, solvent, molarity, selectedOutput) {
        when (selectedOutput) {
            "Soluto" -> viewModel.calculateRequiredSolute()
            "Solvente" -> viewModel.calculateRequiredSolvent()
            "Molalidad" -> viewModel.calculateRequiredMolality()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(Modifier.fillMaxSize().zIndex(0f)) {
                Row(
                    Modifier
                        .width(50.dp)
                        .fillMaxHeight()
                        .padding(bottom = paddingValues.calculateBottomPadding()),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .width(8.dp)
                            .background(MainBlue)
                    )
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(8.dp)
                            .background(Color.Black)
                    )
                }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .background(LightDarkBlue)
                            .fillMaxHeight()
                            .width(17.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxHeight()
                            .width(18.dp)
                            .background(DarkBlue)
                    )
                }
            }
        }

        // Main content
        var scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = navigationBarHeight,
                    top = paddingValues.calculateTopPadding(),
                )
                .verticalScroll(scrollState)
                .zIndex(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Row (
                Modifier.fillMaxWidth()
            ){
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp){
                    viewModel.clearAllInputs()
                    molarMassViewModel.setMolarMassForMolalityCalculator("")
                    molarMassPersonalViewModel.setMolarMassForMolalityCalculator("")
                    navController.navigate(ChemicalUnitsScreenSerializable)
                }
            }
            Spacer(Modifier.height(40.dp))

            Column (Modifier.padding(start = 50.dp).fillMaxSize()){
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Box(Modifier.height(4.dp).fillMaxWidth().background(DarkBlue))
                    Spacer(Modifier.height(4.dp))
                    Text(text = "MOLALIDAD", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp, color = CitrineBrown)
                    Spacer(Modifier.height(4.dp))
                    Box(Modifier.height(4.dp).fillMaxWidth(0.5f).background(DarkBlue))
                }
            }

            Spacer(Modifier.height(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                Text(
                    text = "Encontrar:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(16.dp))
                SelectionMenu(
                    itemList = listOf("Soluto", "Solvente", "Molalidad"),
                    selectedItem = selectedOutput,
                    onItemSelected = { item ->
                        if (calculationResult is CalculationResult.Error) viewModel.clearAllInputs()
                        selectedOutput = item
                    },
                    itemContent = { item ->
                        Text(text = item, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(30.dp))

            CalcTextField(
                input = if (selectedOutput == "Soluto") {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                } else solute,
                onValueChange = { viewModel.onSoluteChange(it) },
                label = "Soluto (g)",
                enable = selectedOutput != "Soluto",
                info = null
            )
            Spacer(Modifier.height(15.dp))

            CalcTextField(
                input = soluteMolarMass,
                onValueChange = { viewModel.onSoluteMolarMassChange(it) },
                label = "Masa molar (g/mol)",
                enable = true,
                info = info
            )

            Spacer(Modifier.height(15.dp))

            CalcTextField(
                input = if (selectedOutput == "Solvente") {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                }    else solvent,
                onValueChange = { viewModel.onSolventChange(it) },
                label = "Solvente (kg)",
                enable = selectedOutput != "Solvente",
                info = null
            )
            Spacer(Modifier.height(15.dp))

            CalcTextField(
                input = if (selectedOutput == "Molalidad") {
                    when (calculationResult) {
                        is CalculationResult.Success -> (calculationResult as CalculationResult.Success).value
                        is CalculationResult.Error -> (calculationResult as CalculationResult.Error).value
                        CalculationResult.Empty -> ""
                    }
                }    else molarity,
                onValueChange = { viewModel.onMolarityChange(it) },
                label = "Molalidad (mol/kg)",
                enable = selectedOutput != "Molalidad",
                info = null
            )
            Spacer(Modifier.height(20.dp))

            AppButton(
                text = "Limpiar",
                width = 120.dp,
                onClick = {
                    viewModel.clearAllInputs()
                    molarMassViewModel.setMolarMassForMolalityCalculator("")
                    molarMassPersonalViewModel.setMolarMassForMolalityCalculator("")                }
            )
        }
    }
}