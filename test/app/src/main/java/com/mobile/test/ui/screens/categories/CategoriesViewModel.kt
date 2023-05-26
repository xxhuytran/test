package com.mobile.test.ui.screens.categories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobile.test.base.BaseViewModel
import com.mobile.test.model.response.CategoryModel
import com.mobile.test.model.response.SignUpResponseModel
import com.mobile.test.utils.SharedPrefs
import kotlinx.coroutines.launch

class CategoriesViewModel: BaseViewModel() {
    var categories = MutableLiveData<MutableList<CategoryModel>>()
    var isEnableSubmit = MutableLiveData<Boolean>()
    private var pageNum: Int = 0
    private var isLoadMore: Boolean = false

    init {
        categories.value = mutableListOf()
        isEnableSubmit.value = false
        getCategories()
    }

    fun onSelectCategory(position: Int) {
        val mCategories = categories.value!!
        mCategories[position].isSelected = !mCategories[position].isSelected
        categories.value = mCategories

        checkEnableSubmit()
    }

    private fun checkEnableSubmit() {
        isEnableSubmit.value = categories.value!!.any { category -> category.isSelected }
    }

    private fun getCategories() {
        coroutineScope.launch {
            val getCategoriesDeferred = appService.getCategoriesAsync(30, pageNum)
            try {
                showLoadingIndicator()
                val newList = getCategoriesDeferred.await()
                var currentList = categories.value!!
                if (isLoadMore) {
                    currentList.addAll(newList.categories)
                } else {
                    currentList = newList.categories
                }

                categories.postValue(currentList)

                hideLoadingIndicator()
            } catch (e: Exception) {
                Log.d("Exception", e.localizedMessage)
                hideLoadingIndicator()
            }
        }
    }

    fun onLoadMore() {
        pageNum++
        isLoadMore = true
        getCategories()
    }
}