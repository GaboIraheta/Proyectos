package com.proyectoPdm.seashellinc.presentation.ui.screens.access

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.model.user.User
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import com.proyectoPdm.seashellinc.utils.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val repository: UserRepository,
    private val connectivityHelper : ConnectivityHelper,
    private val molarMassDao : CompoundDao
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _isLoggedUser = MutableStateFlow<Boolean>(false)
    val isLoggedUser = _isLoggedUser.asStateFlow()

    private val _userToken = MutableStateFlow<String?>("")
    val userToken = _userToken.asStateFlow()

    private val _userId = MutableStateFlow<String?>("")
    val userId = _userId.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()

    fun setUsername(newUsername : String) {
        _username.value = newUsername
    }

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    fun setEmail(newEmail : String) {
        _email.value = newEmail
    }

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    fun setPassword(newPassword : String) {
        _password.value = newPassword
    }

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun setErrorMessage(error : String) {
        _errorMessage.value = error
    }

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _accessSuccess = MutableStateFlow<Boolean>(false)
    val accessSuccess = _accessSuccess.asStateFlow()

    fun logout() {
        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            try {

                repository.logoutUser(userId.value.toString())

                _currentUser.value = null
                _userToken.value = null
                _userId.value = null
                _isLoggedUser.value = false

                _successMessage.value = "Sesion cerrada exitosamente."

            } catch (e : Exception) {
                _errorMessage.value = e.message ?: "Error al cerrar la sesion."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(loginRequest : UserLoginRequest) {

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            if (connectivityHelper.isNetworkAvailable()) {

                val result = repository.loginUser(loginRequest)

                when (result) {
                    is Result.Success -> {
                        val userEntity = result.data.first
                        val token = result.data.second

                        _currentUser.value = userEntity
                        _userToken.value = token
                        _userId.value = userEntity.id
                        _isLoggedUser.value = true

                        _accessSuccess.value = true
                        _successMessage.value = result.message ?: "Inicio de sesion exitoso."

                        _email.value = ""
                        _password.value = ""
                    }

                    is Result.Failure -> {

                        _accessSuccess.value = false
                        _errorMessage.value =
                            result.message ?: "Ha ocurrido un error en el inicio de sesion"

                        _email.value = ""
                        _password.value = ""
                    }
                }
            } else {

                _errorMessage.value = "Conexion a internet requerida para iniciar sesion en tu cuenta de SeaShellCalculator."
                _accessSuccess.value = false

                _email.value = ""
                _password.value = ""
            }

            _isLoading.value = false
        }
    }

    fun registerUser(userRequest: UserRegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            if (connectivityHelper.isNetworkAvailable()) {

                val result = repository.registerUser(userRequest)

                when (result) {
                    is Result.Success -> {

                        val userEntity = result.data

                        _currentUser.value = userEntity
                        _userToken.value = userEntity.token
                        _userId.value = userEntity.id
                        _isLoggedUser.value = true

                        _accessSuccess.value = true
                        _successMessage.value = result.message ?: "Registro de usuario exitoso."

                        _username.value = ""
                        _email.value = ""
                        _password.value = ""
                    }

                    is Result.Failure -> {

                        _accessSuccess.value = false
                        _errorMessage.value =
                            result.message ?: "Ha ocurrido un error en el registro"

                        _username.value = ""
                        _email.value = ""
                        _password.value = ""
                    }
                }
            } else {

                _errorMessage.value = "Conexion a internet requerida para registrarse en SeaShellCalculator."
                _accessSuccess.value = false

                _email.value = ""
                _password.value = ""
            }

            _isLoading.value = false
        }
    }

    private val _newMolarMassName = MutableStateFlow<String>("")
    val newMolarMassName = _newMolarMassName.asStateFlow()

    fun setNewMolarMassName(name : String) {
        _newMolarMassName.value = name
    }

    private val _newMolarMassUnit = MutableStateFlow<String>("")
    val newMolarMassUnit = _newMolarMassUnit.asStateFlow()

    fun setNewMolarMassUnit(unit : String) {
        _newMolarMassUnit.value = unit
    }

    private val _newMolarMassValue = MutableStateFlow<String>("")
    val newMolarMassValue = _newMolarMassValue.asStateFlow()

    fun setNewMolarMassValue(value : String) {
        _newMolarMassValue.value = value
    }

    private val _notValidData = MutableStateFlow<Boolean>(false)
    val notValidData = _notValidData.asStateFlow()

    fun setNotValidData(value : Boolean) {
        _notValidData.value = value
    }

    private val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    fun setError(value : String) {
        _error.value = value
    }

    fun addMolarMass(request : AddMolarMassRequest) {

        viewModelScope.launch {
            _notValidData.value = false
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            if (connectivityHelper.isNetworkAvailable()) {

                when (val result = repository.addMolarMassToList(
                    _userToken.value.toString(),
                    _userId.value.toString(), request
                )) {

                    is Result.Success -> {

                        _successMessage.value =
                            result.message ?: "Masa molar agregada exitosamente."

                        _newMolarMassName.value = ""
                        _newMolarMassValue.value = ""
                        _newMolarMassUnit.value = ""
                    }

                    is Result.Failure -> {
                        _errorMessage.value =
                            result.message ?: "Error al agregar a masa molar a la lista."
                        _notValidData.value = true
                    }
                }
            } else {
                _errorMessage.value = "Conexion a internet requerida para agregar una nueva masa molar a la lista personal."
                _notValidData.value = true
            }

            _isLoading.value = false
        }
    }

    val _isPayPremiumSuccess = MutableStateFlow<Boolean>(false)
    val isPayPremiumSuccess = _isPayPremiumSuccess.asStateFlow()

    fun setIsPayPremiumSuccess(value : Boolean) {
        _isPayPremiumSuccess.value = value
    }

    val _isPayPremiumFailure = MutableStateFlow<Boolean>(false)
    val isPayPremiumFailure = _isPayPremiumFailure.asStateFlow()

    fun updatedPremiumStatus(isPremium : Boolean, isReset : Boolean) {
        viewModelScope.launch {
            _isPayPremiumSuccess.value = false
//            _isPayPremiumFailure.value = false
            _isLoading.value = if (isReset) false else true
            _errorMessage.value = ""
            _successMessage.value = ""

            if (connectivityHelper.isNetworkAvailable() || isReset) {

                when (val result = repository.updateIsPremium(
                    _userToken.value.toString(),
                    _userId.value.toString(),
                    isPremium
                )) {

                    is Result.Success -> {
                        _currentUser.value = result.data
                        if (!isReset)
                            _successMessage.value =
                                result.message ?: "Compra de SeaShellCalculator Premium exitosa."
                        _isPayPremiumSuccess.value = true
                    }

                    is Result.Failure -> {
                        if (!isReset) {
                            _isPayPremiumSuccess.value = false
//                            _isPayPremiumFailure.value = true
                            _errorMessage.value =
                                result.message
                                    ?: "Error en la compra de SeaShellCalculator Premium."
                        }
                    }
                }
            } else {
                if (!isReset) {
                    _isPayPremiumSuccess.value = false
//                    _isPayPremiumFailure.value = true
                    _errorMessage.value =
                        "Conexion a internet requerida para ejecutar la compra de SeaShellCalculator Premium."
                }
            }

            _isLoading.value = false
        }
    }

    fun deleteMolarMassFromList(molarMassId : String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            if (connectivityHelper.isNetworkAvailable()) {

                when (val result = repository.deleteMolarMassFromList(
                    userToken.value.toString(),
                    _userId.value.toString(),
                    molarMassId
                )) {

                    is Result.Success -> {
                        _currentUser.value = result.data
                        _successMessage.value =
                            result.message ?: "Se ha eliminado la masa molar de la lista."
                    }

                    is Result.Failure -> {
                        _errorMessage.value =
                            result.message ?: "Error al eliminar la masa molar de la lista."
                        _notValidData.value = true
                    }
                }
            } else {

                val compoundToDelete = molarMassDao.getMolarMassById(molarMassId)

                if (compoundToDelete != null) {
                    molarMassDao.deleteMolarMass(compoundToDelete)
                } else {
                    _errorMessage.value = "La masa molar no ha podido ser eliminada dado que no se puede encontrar. Conexion a internet requerida."
                    _notValidData.value = true
                }
            }

            _isLoading.value = false
        }
    }

//    private val _isSentEmail = MutableStateFlow<Boolean>(false)
//    val isSentEmail = _isSentEmail.asStateFlow()

//    fun requestRecoveryPassword(email : String) {
//        viewModelScope.launch {
//            _isSentEmail.value = false
//            _isLoading.value = true
//            _errorMessage.value = ""
//            _successMessage.value = ""
//
//            if (_email.value.isEmpty()) {
//                _errorMessage.value = "Ingresa tu correo electronico para solicitar restablecimiento."
//                _isLoading.value = false
//                return@launch
//            }
//
//            if (connectivityHelper.isNetworkAvailable()) {
//
//                when(val result = repository.requestPasswordRecovery(email)) {
//
//                    is Result.Success -> {
//                        _isSentEmail.value = true
//                        _successMessage.value = result.message ?: "Correo electronico de restablecimiento enviado."
//                    }
//
//                    is Result.Failure -> {
//                        _isSentEmail.value = false
//                        _errorMessage.value = result.message ?: "No se pudo solicitar el restablecimiento."
//                    }
//                }
//            } else {
//                _isSentEmail.value = false
//                _errorMessage.value = "Conexion a internet requerida para solicitar restablecimiento."
//            }
//
//            _isLoading.value = false
//        }
//    }

    fun clearSuccessOrErrorMessage() {
        _errorMessage.value = ""
        _successMessage.value = ""
    }

    fun resetAccessSuccessState() {
        _accessSuccess.value = false
    }
}