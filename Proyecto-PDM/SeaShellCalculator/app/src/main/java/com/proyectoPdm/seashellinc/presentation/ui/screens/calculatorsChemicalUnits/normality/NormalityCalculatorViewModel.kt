package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.normality

import androidx.lifecycle.ViewModel
import com.proyectoPdm.seashellinc.data.model.ChemicalCalculator
import com.proyectoPdm.seashellinc.data.model.ChemicalCalculator.ZERO
import com.proyectoPdm.seashellinc.data.model.calculators.CalculationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class NormalityCalculatorViewModel @Inject constructor(): ViewModel() {
    private val _solute = MutableStateFlow<String>("")
    val solute: StateFlow<String> = _solute

    private val _solution = MutableStateFlow<String>("")
    val solution: StateFlow<String> = _solution

    private val _normality = MutableStateFlow<String>("")
    val normality: StateFlow<String> = _normality

    private val _soluteMolarMass = MutableStateFlow<String>("")
    val soluteMolarMass: StateFlow<String> = _soluteMolarMass

    private val _valencyUnits = MutableStateFlow<String>("")
    val valencyUnits: StateFlow<String> = _valencyUnits

    private val _calculationResult = MutableStateFlow<CalculationResult>(CalculationResult.Empty)
    val calculationResult: StateFlow<CalculationResult> = _calculationResult

    fun onSoluteChange(solute: String){
        _solute.value = solute
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSolutionChange(solution: String){
        _solution.value = solution
        _calculationResult.value = CalculationResult.Empty
    }

    fun onNormalityChange(molarity: String){
        _normality.value = molarity
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSoluteMolarMassChange(soluteMolarMass: String){
        _soluteMolarMass.value = soluteMolarMass
        _calculationResult.value = CalculationResult.Empty
    }

    fun onValencyUnitsChange(valencyUnits: String){
        _valencyUnits.value = valencyUnits
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

    fun calculateRequiredSolute() {
        val solutionVal = _solution.value.toBigDecimalOrNull()
        val normalityVal = _normality.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()
        val valencyUnitsVal = _valencyUnits.value.toBigDecimalOrNull()

        if (solutionVal != null && normalityVal != null && soluteMolarMassVal != null && valencyUnitsVal != null){
            if (solutionVal < ZERO || normalityVal < ZERO || soluteMolarMassVal < ZERO || valencyUnitsVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (valencyUnitsVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("Unidades de valencia cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator.calculateSoluteNormality(
                                solution = solutionVal,
                                normality = normalityVal,
                                soluteMolarMass = soluteMolarMassVal,
                                valencyUnits = valencyUnitsVal
                            )
                        },
                        valueToUpdate = _solute
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredSolution() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val normalityVal = _normality.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()
        val valencyUnitsVal = _valencyUnits.value.toBigDecimalOrNull()

        if (soluteVal != null && normalityVal != null && soluteMolarMassVal != null && valencyUnitsVal != null){
            if (soluteVal < ZERO || normalityVal < ZERO || soluteMolarMassVal < ZERO || valencyUnitsVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || normalityVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("División entre cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator
                                .calculateSolutionVolumeOfNormality(
                                    solute = soluteVal,
                                    soluteMolarMass = soluteMolarMassVal,
                                    normality = normalityVal,
                                    valencyUnits = valencyUnitsVal
                                )
                        },
                        valueToUpdate = _solution
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredNormality() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val solutionVal = _solution.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()
        val valencyUnitsVal = _valencyUnits.value.toBigDecimalOrNull()

        if (soluteVal != null && solutionVal != null && soluteMolarMassVal != null && valencyUnitsVal != null){
            if (soluteVal < ZERO || solutionVal < ZERO || soluteMolarMassVal < ZERO || valencyUnitsVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || solutionVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("División entre cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator
                                .calculateConcentrationNormality(
                                    solute = soluteVal,
                                    solution = solutionVal,
                                    soluteMolarMass = soluteMolarMassVal,
                                    valencyUnits = valencyUnitsVal
                                )
                        },
                        valueToUpdate = _normality
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
        _solution.value = ""
        _normality.value = ""
        _soluteMolarMass.value = ""
        _valencyUnits.value = ""
        _calculationResult.value = CalculationResult.Empty
    }
}