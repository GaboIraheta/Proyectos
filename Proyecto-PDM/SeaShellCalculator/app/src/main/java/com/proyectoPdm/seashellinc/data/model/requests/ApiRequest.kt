package com.proyectoPdm.seashellinc.data.model.requests

import com.proyectoPdm.seashellinc.data.model.responses.MolarMassData

data class UserRegisterRequest (
    val username : String,
    val email : String,
    val password : String
)

data class UserLoginRequest (
    val email : String,
    val password : String
)

data class UpdatePremiumRequest (
    val isPremium : Boolean
)

data class UpdateCredentialsRequest (
    val username : String?,
    val email : String?
)

data class UpdatePasswordRequest (
    val newPassword : String
)

data class AddMolarMassRequest (
    val newMolarMass : AddMolarMassData
)

data class AddMolarMassData (
    val name : String,
    val value : Double,
    val unit : String
)

data class PasswordRecoveryRequest (
    val email : String
)

data class ResetPasswordRequest (
    val token : String,
    val newPassword : String
)