package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits

import androidx.lifecycle.ViewModel
import com.proyectoPdm.seashellinc.data.model.calculators.CalculationResult
import com.proyectoPdm.seashellinc.data.model.calculators.Calculator
import com.proyectoPdm.seashellinc.data.model.calculators.Calculator.HUNDRED
import com.proyectoPdm.seashellinc.data.model.calculators.Calculator.ZERO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

class PhysicalCalculatorViewModel: ViewModel() {
    private val _solute = MutableStateFlow<String>("")
    val solute: StateFlow<String> = _solute

    private val _solvent = MutableStateFlow<String>("")
    val solvent: StateFlow<String> = _solvent

    private val _concentration = MutableStateFlow<String>("")
    val concentration: StateFlow<String> = _concentration

    private val _calculationResult = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
    val calculationResult: StateFlow<CalculationResult> = _calculationResult

    fun onSoluteChange(solute: String){
        _solute.value = solute
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSolventChange(solvent: String){
        _solvent.value = solvent
        _calculationResult.value = CalculationResult.Empty
    }

    fun onConcentrationChange(concentration: String){
        _concentration.value = concentration
        _calculationResult.value = CalculationResult.Empty
    }

    private fun handleCalculation(
        calculate: () -> BigDecimal?,
        valueToUpdate: MutableStateFlow<String>
    ) {
        try {
            val result = calculate()
            if (result != null) {
                valueToUpdate.value = result.toPlainString()
                _calculationResult.value = CalculationResult.Success(valueToUpdate.value)
            }
            else {
                _calculationResult.value = CalculationResult.Empty
            }
        }
        catch (e: Exception) {
            _calculationResult.value = CalculationResult.Error("Error de cálculo")
        }

    }

    // Estas funciones son para las calculadoras de masa sobre masa y volumen sobre volumen

    fun calculateRequiredSoluteMMVV() {
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (solventVal != null && concentrationVal != null){
            if (solventVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (concentrationVal >= HUNDRED) {
                    _calculationResult.value = CalculationResult.Error("Porcentaje excesivo")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateSoluteMMVV(solventVal, concentrationVal) },
                        valueToUpdate = _solute
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }

    }

    fun calculateRequiredSolventMMVV() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (soluteVal != null && concentrationVal != null){
            if (soluteVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (concentrationVal == ZERO){
                    _calculationResult.value = CalculationResult.Error("Concentración cero")
                }
                else {
                    if (concentrationVal > HUNDRED) {
                        _calculationResult.value = CalculationResult.Error("Porcentaje excesivo")
                    } else {
                        handleCalculation (
                            calculate = { Calculator.calculateSolventMMVV(soluteVal, concentrationVal) },
                            valueToUpdate = _solvent
                        )
                    }
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateConcentrationPercentageMMVV() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val solventVal =_solvent.value.toBigDecimalOrNull()

        if (solventVal != null && soluteVal != null){
            if (soluteVal < ZERO || solventVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (solventVal.add(soluteVal) == ZERO){
                    _calculationResult.value = CalculationResult.Error("Solución cero")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateConcentrationMMVV(soluteVal, solventVal) },
                        valueToUpdate = _concentration
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    // Estas funciones son para las calculadoras de masa sobre volumen

    fun calculateRequiredSoluteMV() {
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (solventVal != null && concentrationVal != null) {
            if (solventVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                handleCalculation(
                    calculate = { Calculator.calculateSoluteMV(solventVal, concentrationVal) },
                    valueToUpdate = _solute
                )
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredSolventMV() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (soluteVal != null && concentrationVal != null){
            if (soluteVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (concentrationVal == ZERO){
                    _calculationResult.value = CalculationResult.Error("Concentración cero")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateSolventMV(soluteVal, concentrationVal) },
                        valueToUpdate = _solvent
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateConcentrationPercentageMV() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val solventVal =_solvent.value.toBigDecimalOrNull()

        if (solventVal != null && soluteVal != null){
            if (soluteVal < ZERO || solventVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (solventVal == ZERO){
                    _calculationResult.value = CalculationResult.Error("Solvente cero")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateConcentrationMV(soluteVal, solventVal) },
                        valueToUpdate = _concentration
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    // Estas funciones son para las calculadoras de partes por millón

    fun calculateRequiredSolutePPM() {
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (solventVal != null && concentrationVal != null) {
            if (solventVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                handleCalculation(
                    calculate = { Calculator.calculateSolutePPM(solventVal, concentrationVal) },
                    valueToUpdate = _solute
                )
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredSolventPPM() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val concentrationVal =_concentration.value.toBigDecimalOrNull()

        if (soluteVal != null && concentrationVal != null){
            if (soluteVal < ZERO || concentrationVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (concentrationVal == ZERO){
                    _calculationResult.value = CalculationResult.Error("Concentración cero")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateSolventPPM(soluteVal, concentrationVal) },
                        valueToUpdate = _solvent
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateConcentrationPercentagePPM() {
        val soluteVal =_solute.value.toBigDecimalOrNull()
        val solventVal =_solvent.value.toBigDecimalOrNull()

        if (solventVal != null && soluteVal != null){
            if (soluteVal < ZERO || solventVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (solventVal == ZERO){
                    _calculationResult.value = CalculationResult.Error("Solvente cero")
                }
                else {
                    handleCalculation(
                        calculate = { Calculator.calculateConcentrationPPM(soluteVal, solventVal) },
                        valueToUpdate = _concentration
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun clearAllInputs() {
        _solute.value = ""
        _solvent.value = ""
        _concentration.value = ""
        _calculationResult.value = CalculationResult.Empty
    }
}