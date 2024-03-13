package com.trudeals.di.component

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import com.trudeals.core.AppPreferences
import com.trudeals.core.AppSession
import com.trudeals.core.Session
import com.trudeals.data.repository.AuthRepository
import com.trudeals.di.App
import com.trudeals.di.module.ApplicationModule
import com.trudeals.di.module.NetModule
import com.trudeals.di.module.ServiceModule
import com.trudeals.di.module.ViewModelModule
import com.trudeals.utils.validator.Validator
import dagger.BindsInstance
import dagger.Component
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hlink21 on 9/5/16.
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, NetModule::class, ServiceModule::class])
interface ApplicationComponent {

    fun context(): Context

    @Named("cache")
    fun provideCacheDir(): File

    fun provideResources(): Resources

    fun provideCurrentLocale(): Locale

    fun provideViewModelFactory(): ViewModelProvider.Factory

    fun inject(appShell: App)

    fun provideUserRepository(): AuthRepository

    fun provideAppSession(): AppSession

    fun provideAppPreferences(): AppPreferences

    fun provideValidator(): Validator

    fun provideSession() : Session

    @Component.Builder
    interface ApplicationComponentBuilder {
        @BindsInstance
        fun apiKey(@Named("api-key") apiKey: String): ApplicationComponentBuilder

        @BindsInstance
        fun application(application: Application): ApplicationComponentBuilder

        fun build(): ApplicationComponent
    }
}
