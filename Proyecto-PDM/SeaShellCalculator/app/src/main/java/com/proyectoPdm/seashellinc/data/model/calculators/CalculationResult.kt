package com.proyectoPdm.seashellinc.data.model.calculators

sealed interface CalculationResult {
    data class Success(val value: String) : CalculationResult
    data class Error(val value: String) : CalculationResult
    object Empty : CalculationResult
}