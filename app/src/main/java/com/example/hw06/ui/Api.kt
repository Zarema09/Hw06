package com.example.hw06.ui

import android.app.Application
import com.example.hw06.data.modules.dataModule
import com.example.hw06.ui.module.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule)
            modules(uiModule)
        }
    }
}