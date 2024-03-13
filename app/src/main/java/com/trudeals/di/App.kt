package com.trudeals.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.trudeals.BuildConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.INSTANCE.initAppComponent(this, BuildConfig.apiKey)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null
        const val FILE_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"
    }
}
