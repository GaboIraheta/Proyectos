package com.proyectoPdm.seashellinc.billing

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.proyectoPdm.seashellinc.billing.PurchaseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private lateinit var billingClientManager: BillingClientManager

    private val _purchaseStatus = MutableStateFlow<PurchaseStatus>(PurchaseStatus.Idle)
    val purchaseStatus: StateFlow<PurchaseStatus> = _purchaseStatus

    fun initBillingManager() {
        if (!::billingClientManager.isInitialized) {
            billingClientManager = BillingClientManager(getApplication()) { success ->
                Log.d("Billing", "Resultado de la compra: $success")
                _purchaseStatus.value = if (success) {
                    PurchaseStatus.Success
                } else {
                    PurchaseStatus.Error("Compra fallida o cancelada")
                }
            }
        }
    }

    fun launchPurchase(activity: Activity, productId: String) {
        if (!::billingClientManager.isInitialized) {
            _purchaseStatus.value = PurchaseStatus.Error("Billing no inicializado")
            return
        }

        _purchaseStatus.value = PurchaseStatus.Loading
        billingClientManager.launchPurchaseFlow(activity, productId)
    }

    fun verifyPurchases() {
        if (::billingClientManager.isInitialized) {
            billingClientManager.verifyExistingPurchases()
        }
    }

    fun resetStatus() {
        _purchaseStatus.value = PurchaseStatus.Idle
    }
}
