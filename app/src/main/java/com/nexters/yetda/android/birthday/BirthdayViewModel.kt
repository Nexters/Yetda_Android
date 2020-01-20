package com.nexters.yetda.android.birthday

import android.util.Log
import androidx.lifecycle.LiveData
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class BirthdayViewModel : BaseKotlinViewModel(){

    private val TAG = javaClass.simpleName

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    //클릭 이벤트를 받아온다.
    fun clickNextButton() {
        _startNextActivityEvent.call()
    }
}