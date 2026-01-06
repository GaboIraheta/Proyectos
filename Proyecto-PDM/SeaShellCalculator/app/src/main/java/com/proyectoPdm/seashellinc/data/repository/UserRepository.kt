package com.proyectoPdm.seashellinc.data.repository

import android.util.Log
import com.google.gson.Gson
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassRequest
import com.proyectoPdm.seashellinc.data.model.responses.MolarMassData
import com.proyectoPdm.seashellinc.data.model.requests.PasswordRecoveryRequest
import com.proyectoPdm.seashellinc.data.model.requests.ResetPasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdateCredentialsRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePremiumRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.responses.FieldErrorResponse
import com.proyectoPdm.seashellinc.data.model.responses.getErrorMessage
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class UserRepository (
    private val userDao : UserDao,
    private val molarMassDao : CompoundDao,
    private val apiService : ApiService
) {

    private fun parseApiError(errorBody: String?) : String {

        if (errorBody.isNullOrEmpty()) {
            return "Ha ocurrido un error inesperado. Respuesta de error vacía."
        }

        return try {
            val json = JSONObject(errorBody)

            if (json.has("error") && json.get("error") is JSONArray) {
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, FieldErrorResponse::class.java)
                return errorResponse.error.firstOrNull()?.msg
                    ?: "Error de validación: Detalles no disponibles."
            }

            if (json.has("message")) {
                if (json.has("error")) {
                    val errorMessage = json.getString("error")
                    return errorMessage
                }
                return json.getString("message")
            }

            if (json.has("error") && json.get("error") is String) {
                val messagePart = if (json.has("message")) json.getString("message") else ""
                val errorPart = json.getString("error")
                return if (messagePart.isNotBlank() && errorPart.isNotBlank()) {
                    "${messagePart}${errorPart}"
                } else {
                    errorPart
                }
            }

            "Error desconocido: ${errorBody}"

        } catch (e: Exception) {
            return "Error en la comunicación con el servidor. Por favor, inténtelo de nuevo."
        }
    }

    suspend fun registerUser(userRequest : UserRegisterRequest) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.registerUser(userRequest)

                if (response.isSuccessful) {

                    val userResponse = response.body()

                    if (userResponse != null) {
                        val userObjectResponse = userResponse.user

                        val token = userObjectResponse.token
                        val userData = userObjectResponse.user

                        val userEntity = userData.toUserEntity(token)

                        userDao.deleteAllUsers()
                        userDao.registerUserToDatabase(userEntity)
                        userData.toMolarMassEntity().forEach {
                            molarMassDao.addCompound(it)
                        }

                        return@withContext Result.Success(userEntity, userResponse.message)

                    } else {
                        return@withContext Result.Failure(
                            Exception("Problemas con el servidor, respuesta sin contenido."),
                            "El registro ha fallado: Respuesta vacia."
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error en el registro: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e : Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun loginUser(userRequest : UserLoginRequest) : Result<Pair<UserEntity, String>> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.loginUser(userRequest)

                if (response.isSuccessful) {

                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        val loginWrapperResponse = loginResponse.user

                        val token = loginWrapperResponse.token
                        val userData = loginWrapperResponse.user

                        val userEntity = userData.toUserEntity(token)

                        userDao.deleteAllUsers()
                        userDao.registerUserToDatabase(userEntity)
                        userData.toMolarMassEntity().forEach {
                            molarMassDao.addCompound(it)
                        }

                        return@withContext Result.Success(Pair(userEntity, token), loginResponse.message)

                    } else {
                        Result.Failure(
                            Exception("Credenciales de acceso invalidas."),
                            "Credenciales de aceso no validas. Vuelve a intentarlo."
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error en el inicio de sesion: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e : Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun updateIsPremium(token: String, userId: String, isPremium: Boolean): Result<UserEntity> {
        return withContext(Dispatchers.IO) {
            try {

                val request = UpdatePremiumRequest(isPremium)
                val response = apiService.updateIsPremium("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val userResponse = response.body()

                    if (userResponse != null && userResponse.user != null) {

                        val updatedUserData = userResponse.user
                        val updatedMessage = userResponse.message

                        val updatedUserEntity = updatedUserData.toUserEntity(token)

                        userDao.updateUser(updatedUserEntity.copy())

                        val updatedMolarMass = updatedUserData.toMolarMassEntity()
                        molarMassDao.deleteAllMolarMassForUser(userId)
                        molarMassDao.insertAllCompounds(updatedMolarMass)

                        Result.Success(updatedUserEntity, updatedMessage)
                    } else {
                        Result.Failure(
                            Exception("Respuesta invalida en la compra de SeaShellCalculator Premium."),
                            "Error: No se ha podido ejecutar la compra de SeaShellCalculator Premium"
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error en la compra de SeaShellCalculator Premium."),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun getMolarMassList(token : String, userId : String) : Result<List<CompoundEntity>> {
        return try {

            if (token.isNotEmpty() && userId.isNotEmpty()) {
                Result.Failure(Exception("Autenticacion no valida."), "Ha ocurrido un error en la obtencion de la lista de masas molares.")
            }

            val response = apiService.getMolarMassList("Bearer $token", userId)

            if (response.isSuccessful) {

                val compounds = response.body()?.molarMassList?.map {
                    it.toMolarMassEntity(userId)
                }
                    ?: emptyList()
                Result.Success(compounds, "Obteniendo lista de masas molares de usuarios")

            } else {

                val errorMessage = parseApiError(response.errorBody()?.string())
                Result.Failure(Exception(errorMessage), errorMessage)
            }
        } catch (e : Exception) {
            Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
        }
    }

    suspend fun addMolarMassToList(token : String, userId : String, request : AddMolarMassRequest) : Result<Unit> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.addNewMolarMass("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    //todo aqui hay que cambiar la respuesta de la api, tiene que regresar el usuario como los demas de update
                    //no solamente el mensaje de exito, asi se podra actualizar en el room el nuevo usuario y sus masas molares
                    //eso para que cuando el usuario no tenga internet pueda acceder a todas las cosas localmente
                    val addMolarMassResponse = response.body()
                    val message = addMolarMassResponse?.message ?: "Masa molar agregada a la lista."

                    Result.Success(Unit, message)
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al añadir masa molar: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun deleteMolarMassFromList(token : String, userId : String, molarMassApiId : String) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.deleteMolarMass("Bearer $token", userId, molarMassApiId)

                if (response.isSuccessful) {

                    val userResponse = response.body()

                    if (userResponse != null && userResponse.user != null) {

                        val updatedUserData = userResponse.user
                        val updatedMessage = userResponse.message

                        val updatedUserEntity = updatedUserData.toUserEntity(token)
                        userDao.updateUser(updatedUserEntity.copy())

                        val updatedMolarMassEntity = updatedUserData.toMolarMassEntity()
                        molarMassDao.deleteAllMolarMassForUser(userId)
                        molarMassDao.insertAllCompounds(updatedMolarMassEntity)

                        Result.Success(updatedUserEntity, updatedMessage)
                    } else {
                        Result.Failure(
                            Exception("Respuesta no valida al eliminar la masa molar de la lista."),
                            "Error: No se ha podido eliminar la masa molar de la lista."
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al eliminar masa molar: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun logoutUser(userId : String) {
        userDao.deleteUserById(userId)
        molarMassDao.deleteAllMolarMassForUser(userId)
        userDao.deleteAllUsers()
    }

    //todo estos dos ultimos metodos realmente no se usaran ya que no habra funcionalidad de actualizar credenciales ni password para el usuario

    suspend fun updateCredentials(token : String, userId : String, username : String?, email : String?) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = UpdateCredentialsRequest(username, email)
                val response = apiService.updateCredentialsForUser("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)

                    updatedUser?.let {
                        userDao.updateUser(it)
                        Result.Success(it, updatedUserResponse.message)

                    } ?: Result.Failure(
                        Exception("No se pudieron actualizar las credenciales."),
                        "Error al actualizar las credenciales"
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al actualizar credenciales: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun updatePassword(token : String, userId: String, newPassword : String) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = UpdatePasswordRequest(newPassword)
                val response = apiService.updatePasswordForUser("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)

                    updatedUser?.let {
                        userDao.updateUser(it)
                        Result.Success(it, updatedUserResponse.message)

                    } ?: Result.Failure(
                        Exception("No se pudo actualizar la contraseña."),
                        "Error al actualizar la contraseña"
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al actualizar contraseña: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    //todo tambien se va omitir en esta primera version el restablecimiento de password

    suspend fun requestPasswordRecovery(email : String) : Result<Unit> {

        return withContext(Dispatchers.IO) {
            try {

                val request = PasswordRecoveryRequest(email)
                val response = apiService.requestPasswordRecovery(request)

                if (response.isSuccessful) {

                    Log.d("SuccessRecovery", "")

                    Result.Success(Unit, response.body()?.message)

                } else {

                    Log.d("FailureRecovery", "")

                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al solicitar recuperación: ${errorMessage}"),
                        errorMessage
                    )
                }

            } catch (e: Exception) {
                Log.d("ExceptRecovery", "")

                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    //    suspend fun resetPassword(token : String, newPassword : String) : Result<UserEntity> {
//
//        return withContext(Dispatchers.IO) {
//            try {
//
//                val request = ResetPasswordRequest(token, newPassword)
//                val response = apiService.resetPassword(request)
//
//                if (response.isSuccessful) {
//
//                    val userUpdated = response.body()
//                    val user = userUpdated?.user?.user?.toUserEntity(token)
//
//                    user?.let {
//                        Result.Success(it, userUpdated.message)
//
//                    } ?: Result.Failure(
//                        Exception("No se pudo restablecer la contraseña."),
//                        "Error al restablecer la contraseña"
//                    )
//
//                } else {
//                    val errorMessage = parseApiError(response.errorBody()?.string())
//                    Result.Failure(
//                        Exception("Error al restablecer contraseña: ${errorMessage}"),
//                        errorMessage
//                    )
//                }
//
//            } catch (e: Exception) {
//                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
//            }
//        }
//    }
}