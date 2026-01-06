package com.proyectoPdm.seashellinc.data.model.calculators

import java.math.BigDecimal
import java.math.RoundingMode

object Calculator {
    private const val SCALE = 10
    private val ROUNDING_MODE = RoundingMode.HALF_UP

    val HUNDRED = BigDecimal(100)
    val ONE = BigDecimal(1)
    val ZERO = BigDecimal(0)

    // Funciones para masa sobre masa y volumen sobre volumen

    fun calculateSoluteMMVV(solvent: BigDecimal, concentration: BigDecimal): BigDecimal{
        val unitConcentration = concentration.divide(HUNDRED, SCALE, ROUNDING_MODE)
        val divisor = ONE.subtract(unitConcentration)

        return solvent
            .multiply(unitConcentration)
            .divide(divisor, SCALE, ROUNDING_MODE)
    }

    fun calculateSolventMMVV(solute: BigDecimal, concentration: BigDecimal): BigDecimal {
        val unitConcentration = concentration.divide(HUNDRED, SCALE, ROUNDING_MODE)

        return solute
            .subtract(unitConcentration.multiply(solute))
            .divide(unitConcentration, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationMMVV(solute: BigDecimal, solvent: BigDecimal): BigDecimal{
        val concentration = solute.divide(solute.add(solvent), SCALE, ROUNDING_MODE)

        return concentration.multiply(HUNDRED)
    }

    // Funciones para masa sobre volumen

    fun calculateSoluteMV (solvent: BigDecimal, concentration: BigDecimal): BigDecimal{
        val unitConcentration = concentration.divide(HUNDRED, SCALE, ROUNDING_MODE)

        return unitConcentration.multiply(solvent)
    }

    fun calculateSolventMV (solute: BigDecimal, concentration: BigDecimal): BigDecimal{
        val unitConcentration = concentration.divide(HUNDRED, SCALE, ROUNDING_MODE)

        return solute.divide(unitConcentration, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationMV (solute: BigDecimal, solvent: BigDecimal): BigDecimal{
        val concentration = solute.divide(solvent, SCALE, ROUNDING_MODE)

        return concentration.multiply(HUNDRED)
    }

    // Funciones para partes por millón (asumiendo mg y litros)

    fun calculateSolutePPM (solvent: BigDecimal, ppm: BigDecimal): BigDecimal{
        return ppm.multiply(solvent)
    }

    fun calculateSolventPPM (solute: BigDecimal, ppm: BigDecimal): BigDecimal{
        return solute.divide(ppm, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationPPM (solute: BigDecimal, solvent: BigDecimal): BigDecimal{
        return solute.divide(solvent, SCALE, ROUNDING_MODE)
    }
}