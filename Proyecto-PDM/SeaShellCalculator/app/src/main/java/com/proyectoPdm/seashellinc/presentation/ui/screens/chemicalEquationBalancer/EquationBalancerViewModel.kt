package com.proyectoPdm.seashellinc.presentation.ui.screens.chemicalEquationBalancer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.model.balancer.BalanceResult
import com.proyectoPdm.seashellinc.data.model.balancer.Balancer
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//@HiltViewModel
class EquationBalancerViewModel //@Inject constructor()
    : ViewModel() {
    private val _uiState = MutableStateFlow(EquationUiState())
    val uiState: StateFlow<EquationUiState> = _uiState

    fun onFormulaInputChange(newInput: String){
        _uiState.update {
            it.copy(
                formulaInput = newInput,
                errorMessage = null,
                syntaxErrorRange = null,
                balancedEquation = null
            )
        }
    }

    fun onBalanceButtonClicked() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    syntaxErrorRange = null,
                    balancedEquation = null
                )
            }

            val result = Balancer.doBalance(_uiState.value.formulaInput)
            when(result) {
                is BalanceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            balancedEquation = Pair(result.equation, result.coefficients),
                            isLoading = false
                        )
                    }
                }
                is BalanceResult.SyntaxError -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message,
                            syntaxErrorRange = result.start until result.end,
                            isLoading = false
                        )
                    }
                }
                is BalanceResult.BalanceError -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }
                is BalanceResult.UnknownError -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Ha ocurrido un error inesperado",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = EquationUiState()
    }
}