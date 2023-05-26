package com.mobile.test.model.response

import com.google.gson.annotations.SerializedName

data class CategoryResponseModel(
    @SerializedName("categories") val categories: MutableList<CategoryModel>,
)

data class CategoryModel(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    var isSelected: Boolean = false
)