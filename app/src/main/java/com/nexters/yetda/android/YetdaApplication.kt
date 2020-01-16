package com.nexters.yetda.android

import android.app.Application
import com.nexters.yetda.android.di.diModule
import org.koin.android.ext.android.startKoin

class YetdaApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, diModule)
    }
}