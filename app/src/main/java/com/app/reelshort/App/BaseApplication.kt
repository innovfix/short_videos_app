package com.app.reelshort.App

import android.app.Activity
import android.app.Application
import android.net.ConnectivityManager
import android.os.Bundle
import com.app.reelshort.Ads.AdsGoogle
import com.app.reelshort.Model.CommonInfo
import com.app.reelshort.Model.SignUpResponse
import com.app.reelshort.Utils.DPreferences
import com.app.reelshort.Utils.NetworkManager
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    private lateinit var connectivityManager: ConnectivityManager
    lateinit var networkManager: NetworkManager
    var loginResponse: SignUpResponse.ResponseDetails? = null
    var recommended: List<CommonInfo> = emptyList()
    private var mPreferences: DPreferences? = null

    companion object {
        private lateinit var mInstance: BaseApplication

        fun getInstance(): BaseApplication {
            return mInstance
        }

    }

    fun getPrefs(): DPreferences? {
        return mPreferences
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        mPreferences = DPreferences(this)

//        FirebaseApp.initializeApp(this)

        networkManager = NetworkManager.getInstance(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                networkManager.setCurrentActivity(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                networkManager.setCurrentActivity(null)
            }

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}
        })

    }


}