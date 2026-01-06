package com.proyectoPdm.seashellinc.data.model.responses

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class UserResponse (
    val message : String,
    val user : UserObjectResponse
)

data class MolarMassResponse (
    @SerializedName("data")
    val molarMassList : List<MolarMassData>
)

data class UserUpdatedResponse (
    val message : String,
    @SerializedName("user")
    val user : UserData
)

data class MessageResponse (
    val message : String
)

data class ErrorResponse (
    val message : String
)

fun getErrorMessage(response : Response<MolarMassResponse>) : String {

    val errorJson = response.errorBody()?.string()
    val gson = Gson()

    return try {
        gson.fromJson(errorJson, ErrorResponse::class.java).message
    } catch (e : Exception) {
        "Error en la obtencion de las masas molares: $e"
    }
}

data class FieldError (
    val type : String,
    val value : String,
    val msg : String,
    val path : String,
    val location : String
)

data class FieldErrorResponse (
    val error : List<FieldError>
)

fun getErrorMessageList(response : Response<UserResponse>) : String {

    val errorJson = response.errorBody()?.string()
    val gson = Gson()

    val errorMessages = try {
        val errorResponse = gson.fromJson(errorJson, FieldErrorResponse::class.java)
        errorResponse.error.joinToString(separator = "\n") { it.msg }
    } catch (e : Exception) {
        "Error desconocido"
    }

    return errorMessages
}