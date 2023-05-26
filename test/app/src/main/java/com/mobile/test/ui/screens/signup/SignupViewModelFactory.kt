package com.mobile.test.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignupViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java))
            return SignupViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}