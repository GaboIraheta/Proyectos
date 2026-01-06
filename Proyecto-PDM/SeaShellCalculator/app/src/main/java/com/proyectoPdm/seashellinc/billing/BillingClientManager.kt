package com.proyectoPdm.seashellinc.billing

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.billingclient.api.*

class BillingClientManager(
    private val context: Context,
    private val onPurchaseComplete: (Boolean) -> Unit
) : PurchasesUpdatedListener {

    private val billingClient: BillingClient

    init {
        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener(this)
            .build()

        startBillingConnection()
    }

    private fun startBillingConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Handler(Looper.getMainLooper()).postDelayed({
                    startBillingConnection()
                }, 3000)
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    verifyExistingPurchases()
                }
            }
        })
    }

    fun launchPurchaseFlow(activity: Activity, productId: String) {
        val queryParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(queryParams) { billingResult, products ->
            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK || products.isEmpty()) {
                onPurchaseComplete(false)
                return@queryProductDetailsAsync
            }

            val product = products.first()
            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(product)
                            .build()
                    )
                ).build()
            billingClient.launchBillingFlow(activity, billingFlowParams)
        }
    }

    fun verifyExistingPurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { _, purchases ->
            var isPremium = false
            purchases.forEach { purchase ->
                if (purchase.products.contains("premium")) {
                    isPremium = true
                    if (!purchase.isAcknowledged) {
                        val params = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()
                        billingClient.acknowledgePurchase(params) { ackResult ->
                            if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                Log.d("Billing", "Compra reconocida correctamente.")
                            } else {
                                Log.e("Billing", "Error al reconocer la compra: ${ackResult.debugMessage}")
                            }
                        }

                    }
                }
            }
            onPurchaseComplete(isPremium)
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            purchases.forEach { purchase ->
                if (purchase.products.contains("premium")) {
                    if (!purchase.isAcknowledged) {
                        val params = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()
                        billingClient.acknowledgePurchase(params) { ackResult ->
                            onPurchaseComplete(ackResult.responseCode == BillingClient.BillingResponseCode.OK)
                        }
                    } else {
                        onPurchaseComplete(true)
                    }
                }
            }
        } else {
            onPurchaseComplete(false)
        }
    }
}
