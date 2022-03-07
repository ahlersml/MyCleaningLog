package com.example.mycleaninglog

import android.app.Application
import com.google.firebase.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class MyCleaningLogApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin{
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyCleaningLogApplication)
            modules(appModule)
        }
    }
}