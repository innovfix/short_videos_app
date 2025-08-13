package com.app.reelshort.Ads

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.app.reelshort.BuildConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.sdk.AppLovinSdk
import kotlin.math.pow
import com.app.reelshort.R
import kotlin.math.min

class AdsAppLovin(private val context: Context) {
    companion object {
        lateinit var instance: AdsAppLovin
    }

    private val TAG = "AdsAppLovin"
    private var rewardedAppLovinAd: MaxRewardedAd? = null
    private var loadingDialog: AlertDialog? = null
    private var retryAttempt = 0

    init {
        instance = this
    }

    fun showLoadingDialog(activity: Activity) {
        if (loadingDialog?.isShowing == true) return

        val dialog = AlertDialog.Builder(activity)
            .setView(R.layout.loading_dialog_layout)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        loadingDialog = dialog
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    fun loadShowRewardedAd(
        activity: Activity,
        onRewardEarned: (Int) -> Unit,
        onAdDismissed: () -> Unit,
        onAdFailed: () -> Unit,
    ) {
        showLoadingDialog(activity)
        rewardedAppLovinAd = MaxRewardedAd.getInstance("0ec4a62545a5ae26", context as Activity)
        rewardedAppLovinAd?.loadAd()
        rewardedAppLovinAd?.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                retryAttempt = 0
                if (rewardedAppLovinAd?.isReady == true) {
                    rewardedAppLovinAd?.showAd()
                    rewardedAppLovinAd?.loadAd()
                    rewardedAppLovinAd?.destroy()
                } else {
                    dismissLoadingDialog()
                    onAdFailed()
                    rewardedAppLovinAd?.loadAd()
                }
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                retryAttempt = 0
                dismissLoadingDialog()
                onAdFailed.invoke()

            }

            override fun onAdDisplayed(ad: MaxAd) {
                retryAttempt = 0
                onAdDismissed()
            }

            override fun onAdClicked(ad: MaxAd) {
            }

            override fun onAdHidden(ad: MaxAd) {
                rewardedAppLovinAd?.loadAd() // Preload next
                dismissLoadingDialog()
            }

            override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
                // Reward user handled in `showRewardedAd()` callback
                onRewardEarned(1)
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                rewardedAppLovinAd = null
                if (retryAttempt == 4) {
                    dismissLoadingDialog()
                    onAdFailed()
                    return
                }
                retryAttempt++
                loadShowRewardedAd(activity, onRewardEarned, onAdDismissed, onAdFailed)
            }
        })
    }
}
