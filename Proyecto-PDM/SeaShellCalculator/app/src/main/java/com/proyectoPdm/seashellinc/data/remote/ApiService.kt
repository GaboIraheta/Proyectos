package com.proyectoPdm.seashellinc.data.remote

import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassRequest
import com.proyectoPdm.seashellinc.data.model.responses.MessageResponse
import com.proyectoPdm.seashellinc.data.model.responses.MolarMassResponse
import com.proyectoPdm.seashellinc.data.model.requests.PasswordRecoveryRequest
import com.proyectoPdm.seashellinc.data.model.requests.ResetPasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdateCredentialsRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePremiumRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.model.responses.UserResponse
import com.proyectoPdm.seashellinc.data.model.responses.UserUpdatedResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body request : UserRegisterRequest) : Response<UserResponse>

    @POST("login")
    suspend fun loginUser(@Body request : UserLoginRequest) : Response<UserResponse>

    @PUT("premium/{id}")
    suspend fun updateIsPremium(
        @Header("authorization") token : String,
        @Path("id") userId : String,
        @Body request : UpdatePremiumRequest
    ) : Response<UserUpdatedResponse>

    @PUT("credentials/{id}")
    suspend fun updateCredentialsForUser(
        @Header("Authorization") token : String,
        @Path("id") userId : String,
        @Body request : UpdateCredentialsRequest
    ) : Response<UserResponse>

    @PUT("change-password/{id}")
    suspend fun updatePasswordForUser(
        @Header("Authorization") token : String,
        @Path("id") userId : String,
        @Body request : UpdatePasswordRequest
    ) : Response<UserResponse>

    @PUT("addMolarMass/{id}")
    suspend fun addNewMolarMass(
        @Header("authorization") token: String,
        @Path("id") userId: String,
        @Body request: AddMolarMassRequest
    ): Response<UserResponse>

    @GET("getMolarMassList/{id}")
    suspend fun getMolarMassList(
        @Header("authorization") token: String,
        @Path("id") userId: String
    ): Response<MolarMassResponse>

    @DELETE("deleteMolarMass/{userId}/{molarMassId}")
    suspend fun deleteMolarMass(
        @Header("authorization") token: String,
        @Path("userId") userId: String,
        @Path("molarMassId") molarMassId: String
    ): Response<UserUpdatedResponse>

    @POST("request-recovery")
    suspend fun requestPasswordRecovery(@Body request: PasswordRecoveryRequest): Response<MessageResponse>

    //todo esta solicitud no la hace la app, la hace el cliente de la api
//    @PUT("reset-password")
//    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<UserResponse>
}