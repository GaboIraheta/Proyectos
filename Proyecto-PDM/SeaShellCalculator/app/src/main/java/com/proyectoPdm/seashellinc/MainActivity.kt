package com.proyectoPdm.seashellinc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyectoPdm.seashellinc.data.model.user.User
import com.google.android.gms.ads.MobileAds
import com.proyectoPdm.seashellinc.billing.BillingViewModel
import com.proyectoPdm.seashellinc.presentation.navigation.Navigation
import com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PhysicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.SeaShellCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        MobileAds.initialize(this){}

        setContent {
            SeaShellCalculatorTheme {
                val userViewModel : UserViewModel = hiltViewModel()
                val errorViewModel : ErrorViewModel = hiltViewModel()
                val billingViewModel : BillingViewModel = hiltViewModel()
                val molarMassViewModel : MolarMassViewModel = hiltViewModel()
                val molarMassPersonalViewModel : MolarMassPersonalViewModel = hiltViewModel()
                Navigation(userViewModel, errorViewModel, billingViewModel, molarMassViewModel, molarMassPersonalViewModel)
            }
        }
    }
}
