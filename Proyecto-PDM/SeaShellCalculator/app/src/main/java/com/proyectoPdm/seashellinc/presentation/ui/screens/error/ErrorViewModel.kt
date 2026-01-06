package com.proyectoPdm.seashellinc.presentation.ui.screens.error

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor() : ViewModel() {

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun setError(error : String) {
        _errorMessage.value = error
    }
}