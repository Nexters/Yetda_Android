package com.nexters.yetda.android.start

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class StartViewModel : BaseKotlinViewModel(){

    private val TAG = javaClass.simpleName

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    fun clickNextButton(){
        Log.d(TAG, "클릭을하면 이곳으로 옵니다.")
        _startNextActivityEvent.call()
    }
}