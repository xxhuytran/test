package com.mobile.test.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.mobile.test.ui.dialogs.LoadingDialog

open class BaseActivity : AppCompatActivity(), ViewActions {
    private var loadingIndicator: LoadingDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        loadingIndicator?.dismiss()
    }

    override fun showLoadingIndicator(visible: Boolean) {
        if (visible) {
            showLoadingIndicator()
        } else {
            hideLoadingIndicator()
        }
    }

    private fun showLoadingIndicator() {
        if (loadingIndicator == null) {
            loadingIndicator = LoadingDialog(this)
        }
        loadingIndicator?.show()
    }

    private fun hideLoadingIndicator() {
        loadingIndicator?.dismiss()
    }

    fun hideKeyboard() {
        val focusView = currentFocus
        if (focusView != null) {
            val inputMethod = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }
}