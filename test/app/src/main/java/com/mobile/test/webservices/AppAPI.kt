package com.mobile.test.webservices

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mobile.test.model.request.SignUpRequestModel
import com.mobile.test.model.response.*
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface AppAPI {
    @POST("/api/auth/signup")
    fun signupAsync(@Body requestBody: SignUpRequestModel): Deferred<SignUpResponseModel>
}

object AppNetwork {
    private const val TIMEOUT: Long = 120
    private const val BASE_URL = "http://streaming.nexlesoft.com:4000/"
    val appApi: AppAPI

    private val retrofit: Retrofit.Builder by lazy {
        val interceptor = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor {chain -> this.getResponseWithCache(chain)}
            .build()

        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(interceptor)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    init {
        appApi = retrofit.build().create(AppAPI::class.java)
    }

    private fun getResponseWithCache(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
            .newBuilder()
            .build()

        return chain.proceed(request)
    }
}