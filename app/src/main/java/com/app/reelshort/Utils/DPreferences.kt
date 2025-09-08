package com.app.reelshort.Utils

import android.content.Context
import android.content.SharedPreferences

class DPreferences(context: Context) {
    private val prefsHelper = SecurePrefsHelper(context)
    private val mPrefsRead = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val mPrefsWrite = mPrefsRead.edit()
    val AUTH_TOKEN = "AUTHTOKEN"
    val PRIVACY_URL = "privacy_url"
    val TERMS_AND_CONDITIONS = "terms_and_conditions"

    var authToken: String
        get() {
            return prefsHelper.getDecrypted(AUTH_TOKEN)?.let { "Bearer $it" }?:""
        }
        set(value) {
            value?.replace("Bearer ", "")?.let { prefsHelper.saveEncrypted(AUTH_TOKEN, it) }
        }

    var privacyUrl: String?
        get() = mPrefsRead.getString(PRIVACY_URL, "")
        set(value) {
            mPrefsWrite.putString(
                PRIVACY_URL, value
            )
            mPrefsWrite.apply()
        }

    var termsAndConditions: String?
        get() = mPrefsRead.getString(TERMS_AND_CONDITIONS, "")
        set(value) {
            mPrefsWrite.putString(
                TERMS_AND_CONDITIONS, value
            )
            mPrefsWrite.apply()
        }

}
