package com.mobile.test.ui.screens.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobile.test.base.BaseViewModel
import com.mobile.test.model.request.SignUpRequestModel
import com.mobile.test.model.response.SignUpResponseModel
import com.mobile.test.utils.*
import kotlinx.coroutines.launch

class SignupViewModel: BaseViewModel() {
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var isOver16s = MutableLiveData<Boolean>()
    var isShowLoginPassword = MutableLiveData<Boolean>()
    var passwordLevel = MutableLiveData<Int>()
    var isEnableSignUp = MutableLiveData<Boolean>()

    init {
        email.value = ""
        password.value = ""
        isOver16s.value = false
        isEnableSignUp.value = false
        isShowLoginPassword.value = false
        passwordLevel.value = 0

        if (prefs.getLocalSimpleData<SignUpResponseModel>(SharedPrefs.User) != null) {
            val user = prefs.getLocalSimpleData<SignUpResponseModel>(SharedPrefs.User) as SignUpResponseModel
        }
    }

    fun onCheckEnableSignUp() {
        isEnableSignUp.value = email.value!!.isNotEmpty() && email.value!!.isValidEmail() && password.value!!.length >= 6 && password.value!!.length <= 18
    }

    fun onClickShowHidePassword() {
        isShowLoginPassword.value = !isShowLoginPassword.value!!
    }

    fun onCheckPasswordLevel(password: String) {
        when {
            password.isEmpty() -> {
                passwordLevel.value = PasswordLevel.EMPTY.rawValue
                return
            }
            password.length < 6 -> {
                passwordLevel.value = PasswordLevel.SHORT.rawValue
                return
            }
            password.length > 18 -> {
                passwordLevel.value = PasswordLevel.LONG.rawValue
                return
            }
            password.length in 6..18 -> {
                var mPasswordLevel = PasswordLevel.WEEK.rawValue
                if (password.containsLowerAndUpperCase()) {
                    mPasswordLevel += 1
                }
                if (password.containsNumeric()) {
                    mPasswordLevel += 1
                }
                if (password.containsSpecialChar()) {
                    mPasswordLevel += 1
                }

                passwordLevel.value = mPasswordLevel
            }
        }
    }

    fun onClickSignup() {
        if (isEnableSignUp.value == false) {
            return
        }
        val requestModel = SignUpRequestModel(
            "Huy",
            "Tran",
            email.value ?: "",
            password.value ?: ""
        )
        coroutineScope.launch {
            showLoadingIndicator()
            val signupDeferred = appService.signupAsync(requestModel)
            try {
                val response = signupDeferred.await()
                prefs.saveLocalSimpleData(SharedPrefs.User, response)

                hideLoadingIndicator()
            } catch (e: Exception) {
                Log.d("Exception", e.localizedMessage)
                hideLoadingIndicator()
            }
        }
    }
}