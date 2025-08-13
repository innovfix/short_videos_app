package com.app.reelshort.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


val Boolean.toInt: Int get() = if (this) 1 else 0
val Int.asBoolean: Boolean get() = this == 1
val Int.toBoolean: Boolean get() = this == 1

fun Context.isPermissionGranted(): Boolean {
    return NotificationManagerCompat.from(this).areNotificationsEnabled()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("ClickableViewAccessibility")
fun View.disableParentSwipeOnTouch(parent: ViewPager2) {
    setOnTouchListener { v, event ->
        parent.requestDisallowInterceptTouchEvent(false)
        false
    }
}

//fun String.toFormattedDateTime(): String {
//    return try {
//        val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault())
//        inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
//
//        val outputFormat = java.text.SimpleDateFormat("dd-MMMM-yyyy hh:mm a", java.util.Locale.getDefault())
//        val date = inputFormat.parse(this)
//        outputFormat.format(date ?: return this)
//    } catch (e: Exception) {
//        this
//    }
//}

fun String.toFormattedDateTime(): String {
    return try {
        // Parse the input ISO 8601 date string
        val inputFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
        val date = inputFormat.parse(this) ?: return "Invalid Date"

        // Format to desired output
        val outputFormat = SimpleDateFormat("dd-MMMM-yyyy hh:mm a", Locale.getDefault())
        outputFormat.format(date)
    } catch (e: Exception) {
        "Invalid Date"
    }
}


fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}



