package com.example.androidpatterns.ui.main

import android.app.Application
import com.example.androidpatterns.R
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidPatternsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AndroidPatterns)

        startKoin {
            androidLogger()
            androidContext(this@AndroidPatternsApplication)
            modules(appModule)
        }
    }

}
