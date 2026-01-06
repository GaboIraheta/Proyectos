package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molarFraction

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.proyectoPdm.seashellinc.data.model.ChemicalCalculator
import com.proyectoPdm.seashellinc.data.model.ChemicalCalculator.ZERO
import com.proyectoPdm.seashellinc.data.model.calculators.CalculationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MolarFractionCalculatorViewModel @Inject constructor(): ViewModel() {
    private val _solute = MutableStateFlow<String>("")
    val solute: StateFlow<String> = _solute

    private val _soluteMolarMass = MutableStateFlow<String>("")
    val soluteMolarMass: StateFlow<String> = _soluteMolarMass

    private val _solvent = MutableStateFlow<String>("")
    val solvent: StateFlow<String> = _solvent

    private val _solventMolarMass = MutableStateFlow<String>("")
    val solventMolarMass: StateFlow<String> = _solventMolarMass

    private val _molarFraction = MutableStateFlow<String>("")
    val molarFraction: StateFlow<String> = _molarFraction

    private val _calculationResult = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
    val calculationResult: StateFlow<CalculationResult> = _calculationResult

    fun onSoluteChange(solute: String){
        _solute.value = solute
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSoluteMolarMassChange(soluteMolarMass: String){
        _soluteMolarMass.value = soluteMolarMass
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSolventChange(solvent: String){
        _solvent.value = solvent
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSolventMolarMassChange(solventMolarMass: String){
        _solventMolarMass.value = solventMolarMass
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

    fun calculateRequiredSoluteFraction() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val solventMolarMassVal = _solventMolarMass.value.toBigDecimalOrNull()

        if (solventVal != null && soluteVal != null && soluteMolarMassVal != null && solventMolarMassVal != null){
            if (soluteVal < ZERO || solventVal < ZERO || soluteMolarMassVal < ZERO || solventMolarMassVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || solventMolarMassVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("Masa molar cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator.calculateMolarFractionSolute(
                                solute = soluteVal,
                                soluteMolarMass = soluteMolarMassVal,
                                solvent = solventVal,
                                solventMolarMass = solventMolarMassVal
                            )
                        },
                        valueToUpdate = _molarFraction
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredSolventFraction() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val solventMolarMassVal = _solventMolarMass.value.toBigDecimalOrNull()

        if (solventVal != null && soluteVal != null && soluteMolarMassVal != null && solventMolarMassVal != null){
            if (soluteVal < ZERO || solventVal < ZERO || soluteMolarMassVal < ZERO || solventMolarMassVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || solventMolarMassVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("Masa molar cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator.calculateMolarFractionSolvent(
                                solute = soluteVal,
                                soluteMolarMass = soluteMolarMassVal,
                                solvent = solventVal,
                                solventMolarMass = solventMolarMassVal
                            )
                        },
                        valueToUpdate = _molarFraction
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
        _soluteMolarMass.value = ""
        _solvent.value = ""
        _solventMolarMass.value = ""
        _molarFraction.value = ""
        _calculationResult.value = CalculationResult.Empty
    }
}