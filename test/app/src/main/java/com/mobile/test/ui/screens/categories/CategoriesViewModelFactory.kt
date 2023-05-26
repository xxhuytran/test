package com.mobile.test.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.test.ui.screens.signup.SignupViewModel

class CategoriesViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java))
            return CategoriesViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}