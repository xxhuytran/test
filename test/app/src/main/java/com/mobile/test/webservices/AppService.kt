package com.mobile.test.webservices

import com.mobile.test.model.request.SignUpRequestModel
import com.mobile.test.webservices.AppNetwork.appApi

class AppService {
    fun signupAsync(signupRequestModel: SignUpRequestModel) = appApi.signupAsync(signupRequestModel)
}