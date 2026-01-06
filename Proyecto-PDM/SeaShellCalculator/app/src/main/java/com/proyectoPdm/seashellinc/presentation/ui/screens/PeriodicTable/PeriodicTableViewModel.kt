package com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.local.elements
import com.proyectoPdm.seashellinc.data.model.element.Element
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
@HiltViewModel

class PeriodicTableViewModel @Inject constructor() : ViewModel() {

    private val _elementList = mutableStateOf<List<Element>>(emptyList())

    private val _tableByGroup = mutableStateOf<List<List<Element?>>>(emptyList())
    val tableByGroup: State<List<List<Element?>>> = _tableByGroup

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _showDialog = mutableStateOf<Boolean>(false)
    val showDialog : State<Boolean> = _showDialog

    private val _element = mutableStateOf<Element?>(null)
    val element : State<Element?> = _element

    fun changeShowPopUp(element: Element?) {
        _element.value = element
        _showDialog.value = !_showDialog.value
    }

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val listOfElements = elements
                if (listOfElements.isNotEmpty()) {
                    _elementList.value = listOfElements

                    //Se establecen las constantes para las dimensiones de la tabla
                    val maxPeriod = _elementList.value.maxOf { it.period }
                    val maxGroup = 19
                    //Se llena la tabla con los elementos colocados en su periodo y grupo respectivo
                    val table = Array(maxPeriod + 1) { Array<Element?>(maxGroup + 1) { null } }
                    elements.forEach { element ->
                        table[element.period][element.group] = element
                    }
                    _tableByGroup.value = (1..maxGroup).map { group ->
                        (1..maxPeriod).map { period ->
                            table[period][group]
                        }
                    }
                } else {
                    _errorMessage.value = "No se encontraron elementos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
