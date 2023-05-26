package com.mobile.test

import android.app.Application
import android.content.Context

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}