package com.app.reelshort.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.reelshort.App.BaseApplication

object Helper {
    private const val TAG = "Helper"

    fun checkNetworkConnection(): Boolean {
        try {
            val connectivityManager: ConnectivityManager = BaseApplication.getInstance()
                ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = null
            activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return activeNetwork.isConnected
            }
        } catch (e: java.lang.Exception) {
            android.util.Log.e(TAG, "checkNetworkConnection: Exception: " + e.localizedMessage)
        }
        return false
    }

}