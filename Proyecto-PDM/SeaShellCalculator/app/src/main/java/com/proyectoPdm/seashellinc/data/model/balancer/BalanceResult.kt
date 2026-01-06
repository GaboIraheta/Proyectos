package com.proyectoPdm.seashellinc.data.model.balancer

import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.Equation

sealed interface BalanceResult {
    data class Success(
        val equation: Equation,
        val coefficients: List<Int>
    ): BalanceResult

    data class SyntaxError(
        val message: String,
        val formula: String,
        val start: Int,
        val end: Int
    ): BalanceResult

    data class BalanceError(
        val message: String
    ): BalanceResult

    data object UnknownError: BalanceResult
}