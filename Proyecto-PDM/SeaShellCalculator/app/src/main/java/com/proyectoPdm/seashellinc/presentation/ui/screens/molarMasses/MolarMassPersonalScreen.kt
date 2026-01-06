package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassData
import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassRequest
import com.proyectoPdm.seashellinc.presentation.navigation.CompoundScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassPersonalScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.navigation.MolalityCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarFractionCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarityCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.NormalityCalculatorSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppTextField
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily


@Composable
fun MolarMassPersonalScreen(
    navController: NavController,
    viewModel: MolarMassPersonalViewModel,
    userViewModel : UserViewModel,
    backOfPremium : Boolean,
    isCalculator : Boolean,
    screenToBack : String,
    molarMassViewModel: MolarMassViewModel
) {

    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val context = LocalContext.current
    val scroll = rememberScrollState()

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val query by viewModel.query.collectAsState()
    val filteredList by viewModel.filteredList.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    val molarMassName by userViewModel.newMolarMassName.collectAsState()
    val molarMassUnit by userViewModel.newMolarMassUnit.collectAsState()
    val molarMassValue by userViewModel.newMolarMassValue.collectAsState()

    val notValidData by userViewModel.notValidData.collectAsState()
    val error by userViewModel.error.collectAsState()

    val userErrorMessage by userViewModel.errorMessage.collectAsState()
    val successMessage by userViewModel.successMessage.collectAsState()
    val userIsLoading by userViewModel.isLoading.collectAsState()

    DisposableEffect(Unit) {
        viewModel.loadData()

        onDispose {  }
    }

    LaunchedEffect(successMessage) {
        if (successMessage.isNotEmpty()) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        }
        userViewModel.clearSuccessOrErrorMessage()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(bottom = navigationBarHeight)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(8.dp)
                        .background(Color.Black)
                )
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(MainBlue)
                        .height(8.dp)
                )
                Spacer(Modifier.height(30.dp))
            }
        },
        floatingActionButton = {
            if (!isCalculator) {

                Column (
                    verticalArrangement = Arrangement.Center
                ) {

                    FloatingActionButton(
                        onClick = viewModel::changeShowDialog,
                        modifier = Modifier.size(50.dp).shadow(15.dp, RoundedCornerShape(50.dp)),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Agregar",
                            tint = Buff,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    FloatingActionButton(
                        onClick = { viewModel.loadData() },
                        modifier = Modifier.size(50.dp).shadow(15.dp, RoundedCornerShape(50.dp)),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refrescar",
                            tint = Buff,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(20.dp))
            Row {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp) {
                    if (backOfPremium) {
                        navController.navigate(
                            MolarMassScreenSerializable(true, false, "Nothing")
                        )
                        return@AppGoBackButton
                    }
                    navController.popBackStack()
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                if (isCalculator) "Selecciona una\nmasa molar" else "Lista personal\ncompuestos químicos",
                color = CitrineBrown,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = if (isCalculator) 120.dp else 60.dp)
            )

            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTextField(
                        value = query,
                        onValueChange = viewModel::onValueChange,
                        label = "Buscar Compuesto"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MainBlue)
                        .padding(20.dp)
                        .height(500.dp)
                        .fillMaxWidth(0.85f)

                ) {
                    if (isLoading || userIsLoading) {
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
                    } else if (!errorMessage.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .background(Background)
                                .padding(30.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            Text(
                                errorMessage,
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = CitrineBrown
                            )
                        }
                    } else {

                        if (filteredList.isEmpty()) {
                            Text(
                                "No hay masas molares agregadas a la lista.",
                                color = Color.Red,
                                modifier = Modifier
                                    .background(Background)
                                    .padding(30.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .background(Background)
                                    .padding(30.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                            ) {
                                items(filteredList) { item ->
                                    Spacer(Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                if (isCalculator || screenToBack != "Nothing") {
                                                    if (screenToBack == "Molarity") {
                                                        molarMassViewModel.setMolarMassForMolarityCalculator("")
                                                        viewModel.setMolarMassForMolarityCalculator(item.compound.molarMass.toString())
                                                        navController.navigate(
                                                            MolarityCalculatorSerializable)
                                                    } else if (screenToBack == "Molality") {
                                                        molarMassViewModel.setMolarMassForMolalityCalculator("")
                                                        viewModel.setMolarMassForMolalityCalculator(item.compound.molarMass.toString())
                                                        navController.navigate(
                                                            MolalityCalculatorSerializable)
                                                    } else if (screenToBack == "Normality") {
                                                        molarMassViewModel.setMolarMassForNormalityCalculator("")
                                                        viewModel.setMolarMassForNormalityCalculator(item.compound.molarMass.toString())
                                                        navController.navigate(
                                                            NormalityCalculatorSerializable)
                                                    } else if (screenToBack == "MolarFractionSolute") {
                                                        molarMassViewModel.setMolarMassForMolarFractionSoluteCalculator("")
                                                        viewModel.setMolarMassForMolarFractionSoluteCalculator(
                                                            item.compound.molarMass.toString()
                                                        )
                                                        navController.navigate(
                                                            MolarFractionCalculatorSerializable
                                                        )
                                                    }  else if (screenToBack == "MolarFractionSolvent") {
                                                        molarMassViewModel.setMolarMassForMolarFractionSolventCalculator("")
                                                        viewModel.setMolarMassForMolarFractionSolventCalculator(item.compound.molarMass.toString())
                                                        navController.navigate(
                                                            MolarFractionCalculatorSerializable)
                                                    } else return@clickable

                                                    return@clickable
                                                }

                                                navController.navigate(
                                                    CompoundScreenSerializable(item.compound.compoundName, false)
                                                )
                                            }
                                    ) {
                                        Row (
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .horizontalScroll(scroll),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                item.compound.compoundName,
                                                fontFamily = MontserratFontFamily,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            if (!isCalculator) {
                                                TextButton(onClick = {

                                                    userViewModel.setNotValidData(false)
                                                    userViewModel.deleteMolarMassFromList(item.id)

                                                    if (!notValidData) {
                                                        viewModel.loadData()
                                                    }
                                                }) {
                                                    Text(
                                                        "Borrar",
                                                        fontFamily = MontserratFontFamily,
                                                        fontWeight = FontWeight.Bold,
                                                        color = DarkBlue
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Spacer(Modifier.height(7.dp))
                                    HorizontalDivider(color = MainBlue)
                                }
                            }
                        }
                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    containerColor = Background,
                    onDismissRequest = viewModel::changeShowDialog,
                    confirmButton = {
                        TextButton(onClick = {

                            userViewModel.setNotValidData(false)

                            if (molarMassName.isBlank() || molarMassValue.isBlank()) {
                                userViewModel.setNotValidData(true)
                                userViewModel.setError("Campos obligatorios vacios.")
                                return@TextButton
                            }

                            val parsedValue = molarMassValue.toDoubleOrNull()

                            if (parsedValue == null) {
                                userViewModel.setNotValidData(true)
                                userViewModel.setError("Valor no valido para el campo de masa molar. Ingrese un numero decimal.")
                                return@TextButton
                            }

                            if (molarMassUnit.isBlank()) {
                                userViewModel.setNewMolarMassUnit("Formula quimica no agregada.")
                            }

                            val requestData = AddMolarMassData(
                                molarMassName,
                                molarMassValue.toDouble(),
                                molarMassUnit
                            )

                            userViewModel.addMolarMass(AddMolarMassRequest(requestData))

                            if (!notValidData) {
                                //todo aqui por alguna razon cuando hay un error en validacion al agregar justo despues no cierra el dialogo, aunque deberia
                                viewModel.changeShowDialog()
                                viewModel.loadData()
                                userViewModel.clearSuccessOrErrorMessage()
                            }
                        }) {
                            Text(
                                "Agregar",
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        }
                    },
                    title = {
                        Text(
                            "Agregar nuevo compuesto",
                            color = DarkBlue,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MontserratFontFamily
                        ) },
                    dismissButton = {
                        TextButton(onClick = {
                            viewModel.changeShowDialog()
                            userViewModel.clearSuccessOrErrorMessage()
                        }) {
                            Text(
                                "Cerrar",
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        }
                    },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AppTextField(
                                value = molarMassName,
                                onValueChange = { newText ->
                                    userViewModel.setNewMolarMassName(newText)
                                },
                                label = "Nombre del compuesto"
                            )
                            Spacer(Modifier.height(15.dp))
                            AppTextField(
                                value = molarMassUnit,
                                onValueChange = { newText ->
                                    userViewModel.setNewMolarMassUnit(newText)
                                },
                                label = "Fórmula (opcional)"
                            )
                            Spacer(Modifier.height(15.dp))
                            AppTextField(
                                value = molarMassValue,
                                onValueChange = { newText ->
                                    userViewModel.setNewMolarMassValue(newText)
                                },
                                label = "Masa molar"
                            )
                            Spacer(Modifier.height(10.dp))
                            if (notValidData) {
                                Text(
                                    text = if (error.isNotEmpty()) error else if (userErrorMessage.isNotEmpty()) userErrorMessage else "Error",
                                    color = Color.Red
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}