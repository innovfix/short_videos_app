package com.app.reelshort.Ads

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.app.reelshort.R
import com.app.reelshort.Utils.showToast
import com.app.reelshort.ViewModel.ApiResult
import com.app.reelshort.ViewModel.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdsGoogle(private val context: Context) {
    companion object {
        lateinit var instance: AdsGoogle
    }

    private var rewardedAd: RewardedAd? = null
    private val TAG = "AdsGoogle"
    private val AD_UNIT_ID = "/6499/example/rewarded" // Test Ad Unit ID


    private var loadingDialog: AlertDialog? = null


    init {
        instance = this@AdsGoogle
        MobileAds.initialize(context) {

        }
    }

    fun showLoadingDialog(activity: Activity) {

        if (loadingDialog?.isShowing == true) return
//
//        loadingDialog = ProgressDialog.show(activity, "Please Wait...", "Loading Ads", true)
//        loadingDialog?.setCancelable(false)
//        loadingDialog?.show()


        val dialog = AlertDialog.Builder(activity)
            .setView(R.layout.loading_dialog_layout) // Create your custom loading layout
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        loadingDialog = dialog
    }

    //
    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    var counter = 0
    fun loadShowRewardedAd(
        activity: Activity, onRewardEarned: (Int) -> Unit,
        onAdDismissed: () -> Unit, onAdFailed: () -> Unit,
    ) {
        val adRequest = AdRequest.Builder().build()
        showLoadingDialog(activity)
        RewardedAd.load(
            activity,
            AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    ad.let { ad ->
                        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                rewardedAd = null
                                counter = 0
                                onAdDismissed.invoke()
                                dismissLoadingDialog()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                rewardedAd = null
                                counter = 0
//                                dismissLoadingDialog()
                            }
                        }
                        ad.show(activity) { rewardItem ->
                            dismissLoadingDialog()
                            counter = 0
                            onRewardEarned.invoke(rewardItem.amount)
                        }
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    if (counter == 4) {
                        dismissLoadingDialog()
                        onAdFailed()
                        return
                    }
                    counter++
                    loadShowRewardedAd(activity, onRewardEarned, onAdDismissed, onAdFailed)
                }
            }
        )
    }
}