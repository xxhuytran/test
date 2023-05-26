package com.mobile.test.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.mobile.test.MainApplication
import com.mobile.test.common.SingleLiveEvent
import com.mobile.test.utils.NavigationCommand
import com.mobile.test.utils.SharedPrefs
import com.mobile.test.webservices.AppService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel() {
    var viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    val appService = AppService()
    val prefs = SharedPrefs(MainApplication.appContext)

    private val _navigationCommands = SingleLiveEvent<NavigationCommand>()
    val navigationCommands: LiveData<NavigationCommand>
        get() = _navigationCommands

    fun showLoadingIndicator(){
        _navigationCommands.postValue(NavigationCommand.ShowLoadingIndicator(true))
    }

    fun hideLoadingIndicator(){
        _navigationCommands.postValue(NavigationCommand.ShowLoadingIndicator(false))
    }

    /**
     * Navigate to a fragment
     */
    fun navigate(directions: NavDirections) {
        _navigationCommands.postValue(NavigationCommand.To(directions))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}