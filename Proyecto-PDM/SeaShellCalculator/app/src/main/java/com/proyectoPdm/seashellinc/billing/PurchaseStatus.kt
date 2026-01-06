package com.proyectoPdm.seashellinc.billing

sealed class PurchaseStatus {
    object Idle : PurchaseStatus()
    object Loading : PurchaseStatus()
    object Success : PurchaseStatus()
    data class Error(val message: String) : PurchaseStatus()
}
