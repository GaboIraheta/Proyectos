package com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.navigation.MainScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.ElementCard
import com.proyectoPdm.seashellinc.presentation.ui.components.ElementDialog
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Composable
fun PeriodicTableScreen(
    navController: NavController,
    backOfPremium : Boolean,
    periodicTableViewModel: PeriodicTableViewModel = hiltViewModel()
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val tableByGroup = periodicTableViewModel.tableByGroup.value
    val isLoading = periodicTableViewModel.isLoading.value
    val errorMessage = periodicTableViewModel.errorMessage.value
    val showDialog = periodicTableViewModel.showDialog.value
    val element = periodicTableViewModel.element.value

    Scaffold(
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
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MainBlue),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LogoComponent(modifier = Modifier.size(60.dp), 1f)
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 10.dp)
                ) {
                    Spacer(Modifier.width(10.dp))
                    AppGoBackButton(60.dp) {
                        if (backOfPremium) {
                            navController.navigate(MainScreenSerializable)
                            return@AppGoBackButton
                        }
                        navController.popBackStack()
                    }
                    Text(
                        "Tabla Periódica\nde los elementos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown,
                        fontSize = 20.sp
                    )
                }

                if (isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = MainBlue)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, top = 20.dp)
                    ) {
                        item {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 600.dp)
                            ) {
                                val scrollState = rememberScrollState()

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(scrollState)
                                        .padding(8.dp)
                                ) {
                                    tableByGroup.forEachIndexed { groupIndex, column ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            //modifier = Modifier.padding(horizontal = 4.dp)
                                        ) {
                                            column.forEachIndexed { periodIndex, element ->
                                                if (periodIndex == 7) {
                                                    Spacer(modifier = Modifier.height(16.dp))
                                                }
                                                if (element != null) {
                                                    ElementCard(element = element, onClick = {
                                                        periodicTableViewModel.changeShowPopUp(element)
                                                    })
                                                } else {
                                                    Spacer(modifier = Modifier.size(75.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog){
            ElementDialog(
                element, changeShowDialog = { periodicTableViewModel.changeShowPopUp(element) },
            )
        }
    }
}