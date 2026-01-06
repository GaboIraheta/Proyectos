package com.proyectoPdm.seashellinc.data.model.balancer

import com.proyectoPdm.seashellinc.utils.buildMatrix
import com.proyectoPdm.seashellinc.utils.checkAnswer
import com.proyectoPdm.seashellinc.utils.extractCoefficients
import com.proyectoPdm.seashellinc.utils.solve

object Balancer {
    fun doBalance(formula: String): BalanceResult {
        try {
            var eqn = Parser(formula).parseEquation()
            var matrix = buildMatrix(eqn)
            val coefs = extractCoefficients(solve(matrix)).map { it.toInt() }
            checkAnswer(eqn, coefs.toIntArray())
            return BalanceResult.Success(eqn, coefs)
        }
        catch (e: ParseError){
            val start = e.start
            var end = e.end ?: e.start
            while (end > start && formula.getOrNull(end - 1)?.isWhitespace() == true) end --
            if (start == end) end++
            return BalanceResult.SyntaxError("Error de sintaxis: ${e.message}", formula, start, end)
        } catch (e: IllegalStateException) {
            return BalanceResult.BalanceError(e.message ?: "Error desconocido al balancear")
        } catch (e: Exception) {
            return BalanceResult.UnknownError
        }
    }
}