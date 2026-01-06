package com.proyectoPdm.seashellinc.presentation.ui.screens.chemicalEquationBalancer

import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.Equation

data class EquationUiState (
    val formulaInput: String = "",
    val errorMessage: String? = null,
    val syntaxErrorRange: IntRange? = null,
    val balancedEquation: Pair<Equation, List<Int>>? = null,
    val isLoading: Boolean = false
)