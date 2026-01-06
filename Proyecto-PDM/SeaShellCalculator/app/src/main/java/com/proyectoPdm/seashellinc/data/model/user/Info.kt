package com.proyectoPdm.seashellinc.data.model.user

import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel

data class Info (
    val isLoggedUser : Boolean,
    val currentUser : UserEntity?,
    val navController: NavController,
    val errorViewModel: ErrorViewModel,
    val screen : String,
    val screenPremium : String = "MolarMassPersonal"
)