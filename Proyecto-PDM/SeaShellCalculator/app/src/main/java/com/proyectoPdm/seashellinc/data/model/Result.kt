package com.proyectoPdm.seashellinc.data.model

sealed class Result<out T> {
    data class Success<out T>(val data: T, val message : String? = null) : Result<T>()
    data class Failure(val exception: Exception, val message : String? = null) : Result<Nothing>()
}