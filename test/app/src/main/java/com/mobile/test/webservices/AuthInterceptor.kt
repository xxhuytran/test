package com.mobile.test.webservices

import android.util.Log
import com.mobile.test.MainApplication
import com.mobile.test.model.response.SignUpResponseModel
import com.mobile.test.utils.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val prefs = SharedPrefs(MainApplication.appContext)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        (prefs.getLocalSimpleData<SignUpResponseModel>(SharedPrefs.User) as SignUpResponseModel?).let { userModel ->
            userModel?.let {
                it.token.let { token -> requestBuilder.addHeader("Authorization", "Bearer $token") }

                Log.d("Authorization token", it.token)
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}