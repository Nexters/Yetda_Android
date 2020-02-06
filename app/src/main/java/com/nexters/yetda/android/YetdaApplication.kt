package com.nexters.yetda.android

import android.app.Application
import com.nexters.yetda.android.di.diModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin

class YetdaApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, diModule)

        // Realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("myrealm.realm")
            .deleteRealmIfMigrationNeeded() // 릴리즈 전에 수정해야됨
            .build()
        Realm.setDefaultConfiguration(config)
    }
}