package com.mobile.test.utils

import android.graphics.Color
import android.os.SystemClock
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import java.util.regex.Pattern

fun CharSequence.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence.containsLowerAndUpperCase(): Boolean {
    val regex = "(?=.*[a-z])(?=.*[A-Z])".toRegex()
    return regex.containsMatchIn(this)
}

fun CharSequence.containsNumeric(): Boolean {
    val regex = Regex("\\d+")
    return regex.containsMatchIn(this)
}

fun CharSequence.containsSpecialChar(): Boolean {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.containsMatchIn(this)
}

fun SpannableString.setSpannableStringWithAction(text: String, linkText: String, linkTextColor: Int,action: (() -> Unit)?): SpannableString {
    var mLastClickTime: Long = 0
    val firstIndex = text.indexOf(linkText)
    val lastIndex = firstIndex + linkText.length
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            // prevent double click
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            action?.invoke()
        }
    }
    this.setSpan(clickableSpan, firstIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.setSpan(ForegroundColorSpan(linkTextColor), firstIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}