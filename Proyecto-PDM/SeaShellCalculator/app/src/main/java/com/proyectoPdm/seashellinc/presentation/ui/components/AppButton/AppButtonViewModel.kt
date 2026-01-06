package com.proyectoPdm.seashellinc.presentation.ui.components.AppButton

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AppButtonViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var interstitialAd: InterstitialAd? = null
    private var onAdDismissed: (() -> Unit)? = null

    private val _showAdEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val showAdEvent: SharedFlow<Unit> = _showAdEvent

    init {
        loadAd()
    }

    private fun loadAd() {
        interstitialAd = null
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712", //TODO: Cuando se vaya a producción, deberá de cambiarse el id, contactar a Diego
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun onButtonClicked(showAd: Boolean = true, onContinue: () -> Unit) {
        val shouldShowAd = showAd && (1..100).random() <= 30

        if (shouldShowAd && interstitialAd != null) {
            onAdDismissed = onContinue
            _showAdEvent.tryEmit(Unit)
        } else {
            onContinue()
        }
    }

    fun showAdIfAvailable(activity: Activity) {

        interstitialAd?.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadAd()
                    onAdDismissed?.invoke()
                    onAdDismissed = null
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    onAdDismissed?.invoke()
                    onAdDismissed = null
                }
            }
            show(activity)
        }
    }
}