package com.shivangi.globalinvestai.android

import android.app.Application
import com.shivangi.globalinvestai.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@App)
            androidLogger()
        }
    }
}