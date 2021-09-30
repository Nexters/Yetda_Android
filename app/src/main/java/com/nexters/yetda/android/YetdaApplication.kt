package com.nexters.yetda.android

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.airbnb.lottie.LottieAnimationView
import io.realm.Realm
import io.realm.RealmConfiguration

class YetdaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        startKoin(applicationContext, diModule)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.notification_channel_id)
            val channelName = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("myrealm.realm")
            .deleteRealmIfMigrationNeeded() // 릴리즈 전에 수정해야됨
            .build()
        Realm.setDefaultConfiguration(config)
    }


    init {
        instance = this
    }

    companion object {
        private var instance: YetdaApplication? = null

        fun get(): YetdaApplication {
            return instance!!
        }
    }

    /**
     * Progress Dialog
     * HOW TO USE
     * YetdaApplication.get().progressON(JoinActivity.this);
     * YetdaApplication.get().progressOFF();
     */
    internal var progressDialog: AppCompatDialog? = null

    fun progressON(activity: Activity?) {
        if (activity == null) {
            return
        }
        if (progressDialog != null && progressDialog!!.isShowing) {
            //            progressSET(message);
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog!!.setCancelable(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.progress_loading)
            progressDialog!!.show()
        }

        val lottieAnimationView =
            progressDialog!!.findViewById<View>(R.id.progress_lottie) as LottieAnimationView?
        lottieAnimationView!!.playAnimation()
    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

}