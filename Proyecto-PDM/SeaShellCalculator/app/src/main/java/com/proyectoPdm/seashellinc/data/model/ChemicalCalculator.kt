package com.proyectoPdm.seashellinc.data.model

import java.math.BigDecimal
import java.math.RoundingMode

object ChemicalCalculator {
    private const val SCALE = 10
    private val ROUNDING_MODE = RoundingMode.HALF_UP

    val HUNDRED = BigDecimal(100)
    val ONE = BigDecimal(1)
    val ZERO = BigDecimal(0)

    // Funciones para calcular MOLARIDAD

    fun calculateSoluteMolarity(
        solution: BigDecimal,
        soluteMolarMass: BigDecimal,
        molarity: BigDecimal
    ): BigDecimal {
        return molarity
            .multiply(solution)
            .multiply(soluteMolarMass)
            .setScale(SCALE, ROUNDING_MODE)
    }

    fun calculateSolutionVolumeOfMolarity(
        solute: BigDecimal,
        soluteMolarMass: BigDecimal,
        molarity: BigDecimal
    ): BigDecimal {
        val denominator = molarity.multiply(soluteMolarMass)
        return solute.divide(denominator, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationMolarity(
        solute: BigDecimal,
        solution: BigDecimal,
        soluteMolarMass: BigDecimal
    ): BigDecimal {
        val denominator = soluteMolarMass.multiply(solution)
        return solute.divide(denominator, SCALE, ROUNDING_MODE)
    }

    // Funciones para calcular MOLALIDAD
    // son muy similares, pero una recibe solución como argumento, y la otra recibe un solvente...
    // y dado que es masa con volumen, no es cómo que se pueda unificar solución sumando soluto y solvente
    // y me caga lo similares que son las funciones; pero así se va

    fun calculateSoluteMolality(
        solvent: BigDecimal,
        soluteMolarMass: BigDecimal,
        molarity: BigDecimal
    ): BigDecimal {
        return molarity
            .multiply(solvent)
            .multiply(soluteMolarMass)
            .setScale(SCALE, ROUNDING_MODE)
    }

    fun calculateSolventMassOfMolality(
        solute: BigDecimal,
        soluteMolarMass: BigDecimal,
        molarity: BigDecimal
    ): BigDecimal {
        val denominator = molarity.multiply(soluteMolarMass)
        return solute.divide(denominator, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationMolality(
        solute: BigDecimal,
        solvent: BigDecimal,
        soluteMolarMass: BigDecimal
    ): BigDecimal {
        val denominator = soluteMolarMass.multiply(solvent)
        return solute.divide(denominator, SCALE, ROUNDING_MODE)
    }

    // Funciones para calcular NORMALIDAD

    fun calculateSoluteNormality(
        solution: BigDecimal,
        soluteMolarMass: BigDecimal,
        valencyUnits: BigDecimal,
        normality: BigDecimal
    ): BigDecimal {
        return soluteMolarMass
            .multiply(solution)
            .multiply(normality)
            .divide(valencyUnits, SCALE, ROUNDING_MODE)
    }

    fun calculateSolutionVolumeOfNormality(
        solute: BigDecimal,
        soluteMolarMass: BigDecimal,
        valencyUnits: BigDecimal,
        normality: BigDecimal
    ): BigDecimal {
        val denominator = normality.multiply(soluteMolarMass)
        return solute
            .multiply(valencyUnits)
            .divide(denominator, SCALE, ROUNDING_MODE)
    }

    fun calculateConcentrationNormality(
        solute: BigDecimal,
        solution: BigDecimal,
        soluteMolarMass: BigDecimal,
        valencyUnits: BigDecimal,
    ): BigDecimal {
        val denominator = solution.multiply(soluteMolarMass)
        return solute
            .multiply(valencyUnits)
            .divide(denominator, SCALE, ROUNDING_MODE)
    }

    // Funciones para calcular FRACCIÓN MOLAR

    fun calculateMolarFractionSolute(
        solute: BigDecimal,
        solvent: BigDecimal,
        soluteMolarMass: BigDecimal,
        solventMolarMass: BigDecimal
    ): BigDecimal {
        val denominator = solute.multiply(solventMolarMass)
            .add(solvent.multiply(soluteMolarMass))
        return solute
            .multiply(solventMolarMass)
            .divide(denominator, SCALE, ROUNDING_MODE)
    }

    fun calculateMolarFractionSolvent(
        solute: BigDecimal,
        solvent: BigDecimal,
        soluteMolarMass: BigDecimal,
        solventMolarMass: BigDecimal
    ): BigDecimal {
        val denominator = solute.multiply(solventMolarMass)
            .add(solvent.multiply(soluteMolarMass))
        return solvent
            .multiply(soluteMolarMass)
            .divide(denominator, SCALE, ROUNDING_MODE)
    }
}