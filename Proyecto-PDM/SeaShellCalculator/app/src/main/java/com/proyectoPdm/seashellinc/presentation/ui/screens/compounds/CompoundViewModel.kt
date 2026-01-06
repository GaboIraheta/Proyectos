package com.proyectoPdm.seashellinc.presentation.ui.screens.compounds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.SeaShellChemistryDatabase
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.data.local.compounds
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompoundViewModel @Inject constructor(
    private val repository : UserRepository,
    private val db : SeaShellChemistryDatabase
) : ViewModel() {
    private val _staticCompoundList = MutableStateFlow<List<Compound>>(emptyList())

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _compound = MutableStateFlow<Compound?>(null)
    val compound = _compound.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val staticCompounds = compounds
                if (compounds.isNotEmpty()) {
                    _staticCompoundList.value = staticCompounds
                } else {
                    _errorMessage.value = "No se ha podido obtener los datos"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
        _isLoading.value = false
    }

    fun getCompound(compoundName: String, static: Boolean = true) {

        _isLoading.value = true

        if (static) {
            val compound = _staticCompoundList.value.find {
                it.compoundName.contains(
                    compoundName,
                    ignoreCase = true
                )
            }
            _compound.value = if (compound?.compoundName == compoundName) {
                compound
            } else null

        } else {
            viewModelScope.launch {
                _compound.value = getCompoundForMolarMassPersonal(compoundName, db.UserDao())
            }
        }

        _isLoading.value = false
    }

    suspend fun getCompoundForMolarMassPersonal(compoundName : String, dao : UserDao) : Compound? {
        val userLogged = dao.getLoggedUser()
        val result = repository.getMolarMassList(userLogged?.token ?: "", userLogged?.id ?: "")
        return if (result is Result.Success) {
            result.data.find {
                it.compound.compoundName.equals(compoundName, ignoreCase = true)
            }?.compound
        } else null
    }
}