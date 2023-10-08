package com.polytechtest

import android.app.Application
import com.polytechtest.di.dataBaseModule
import com.polytechtest.di.mapperModule
import com.polytechtest.di.networkModule
import com.polytechtest.di.providerModule
import com.polytechtest.di.repositoryModule
import com.polytechtest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(listOf(viewModelModule, networkModule, providerModule, repositoryModule, mapperModule, dataBaseModule))
        }
    }
}