package com.mobile.test.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpResponseModel(
    @SerializedName("_id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("admin") val isAdmin: Boolean,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val version: Int,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("token") val token: String,
    @SerializedName("refreshToken") val refreshToken: String,
)
