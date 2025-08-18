package com.app.reelshort.Utils

import android.content.Context

class DPreferences(context: Context) {

    private val prefsHelper = SecurePrefsHelper(context)
    private val plainPrefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = plainPrefs.edit()

    var authToken: String
        get() = prefsHelper.getDecrypted("AUTHTOKEN")?.let { "Bearer $it" }?:""
        set(value) {
            value?.replace("Bearer ", "")?.let { prefsHelper.saveEncrypted("AUTHTOKEN", it) }
        }

}
