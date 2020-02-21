package com.nexters.yetda.android.util

import android.app.Activity
import android.os.Process
import android.widget.Toast


class BackPressCloseHandler(private val activity: Activity) {
    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.moveTaskToBack(true)
            activity.finish()
            Process.killProcess(Process.myPid())
            toast!!.cancel()
        }
    }

    private fun showGuide() {
        toast = Toast.makeText(
            activity, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.",
            Toast.LENGTH_SHORT
        )
        toast!!.show()
    }

}