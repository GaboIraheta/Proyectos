package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molality

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
class MolalityCalculatorViewModel @Inject constructor(): ViewModel() {
    private val _solute = MutableStateFlow<String>("")
    val solute: StateFlow<String> = _solute

    private val _solvent = MutableStateFlow<String>("")
    val solvent: StateFlow<String> = _solvent

    private val _molarity = MutableStateFlow<String>("")
    val molarity: StateFlow<String> = _molarity

    private val _soluteMolarMass = MutableStateFlow<String>("")
    val soluteMolarMass: StateFlow<String> = _soluteMolarMass

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

    fun onMolarityChange(molarity: String){
        _molarity.value = molarity
        _calculationResult.value = CalculationResult.Empty
    }

    fun onSoluteMolarMassChange(soluteMolarMass: String){
        _soluteMolarMass.value = soluteMolarMass
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
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val molarityVal = _molarity.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()

        if (solventVal != null && molarityVal != null && soluteMolarMassVal != null){
            if (solventVal < ZERO || molarityVal < ZERO || soluteMolarMassVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                handleCalculation(
                    calculate = {
                        ChemicalCalculator.calculateSoluteMolality(
                            solvent = solventVal,
                            molarity = molarityVal,
                            soluteMolarMass = soluteMolarMassVal
                        )
                    },
                    valueToUpdate = _solute
                )
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredSolvent() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val molarityVal = _molarity.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()

        if (soluteVal != null && molarityVal != null && soluteMolarMassVal != null){
            if (soluteVal < ZERO || molarityVal < ZERO || soluteMolarMassVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || molarityVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("División entre cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator
                                .calculateSolventMassOfMolality(
                                    solute = soluteVal,
                                    soluteMolarMass = soluteMolarMassVal,
                                    molarity = molarityVal
                                )
                        },
                        valueToUpdate = _solvent
                    )
                }
            }
        }
        else {
            _calculationResult.value = CalculationResult.Empty
        }
    }

    fun calculateRequiredMolality() {
        val soluteVal = _solute.value.toBigDecimalOrNull()
        val solventVal = _solvent.value.toBigDecimalOrNull()
        val soluteMolarMassVal = _soluteMolarMass.value.toBigDecimalOrNull()

        if (soluteVal != null && solventVal != null && soluteMolarMassVal != null){
            if (soluteVal < ZERO || solventVal < ZERO || soluteMolarMassVal < ZERO){
                _calculationResult.value = CalculationResult.Error("Valores negativos")
            }
            else {
                if (soluteMolarMassVal == ZERO || solventVal == ZERO) {
                    _calculationResult.value = CalculationResult.Error("División entre cero")
                }
                else {
                    handleCalculation(
                        calculate = {
                            ChemicalCalculator
                                .calculateConcentrationMolality(
                                    solute = soluteVal,
                                    solvent = solventVal,
                                    soluteMolarMass = soluteMolarMassVal
                                )
                        },
                        valueToUpdate = _molarity
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
        _molarity.value = ""
        _soluteMolarMass.value = ""
        _calculationResult.value = CalculationResult.Empty
    }
}