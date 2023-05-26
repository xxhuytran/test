package com.mobile.test.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequestModel(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)