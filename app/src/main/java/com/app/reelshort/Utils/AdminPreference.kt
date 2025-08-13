package com.app.reelshort.Utils

import android.content.Context

class AdminPreference(context: Context) {

    private val prefsHelper = SecurePrefsHelper(context)
    private val plainPrefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = plainPrefs.edit()

    var isLogin: Boolean
        get() = plainPrefs.getBoolean("IS_LOGIN", false)
        set(value) {
            editor.putBoolean("IS_LOGIN", value).apply()
        }

    var authToken: String
        get() = prefsHelper.getDecrypted("AUTHTOKEN")?.let { "Bearer $it" } ?: "Bearer "
        set(value) {
            prefsHelper.saveEncrypted("AUTHTOKEN", value.replace("Bearer ", ""))
        }

    var email: String
        get() = prefsHelper.getDecrypted("EMAIL") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("EMAIL", value)
        }

    var uid: String
        get() = prefsHelper.getDecrypted("UID") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("UID", value)
        }

    var loginType: String
        get() = prefsHelper.getDecrypted("LOGIN_TYPE") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("LOGIN_TYPE", value)
        }

    var profilePicture: String
        get() = prefsHelper.getDecrypted("LOGIN_PROFILE_URL") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("LOGIN_PROFILE_URL", value)
        }

    var loginTypeId: String
        get() = prefsHelper.getDecrypted("LOGIN_TYPE_ID") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("LOGIN_TYPE_ID", value)
        }

    var name: String
        get() = prefsHelper.getDecrypted("LOGIN_NAME") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("LOGIN_NAME", value)
        }

    var deviceToke: String
        get() = prefsHelper.getDecrypted("DEVICE_TOKEN") ?: ""
        set(value) {
            prefsHelper.saveEncrypted("DEVICE_TOKEN", value)
        }

    fun logoutClean() {
        isLogin = false
        authToken = ""
        email = ""
        uid = ""
        loginType = ""
        profilePicture = ""
        name = ""
        loginTypeId = ""
        deviceToke = ""
    }
}
