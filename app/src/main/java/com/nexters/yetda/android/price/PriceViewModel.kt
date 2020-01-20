package com.nexters.yetda.android.price

import android.util.Log
import androidx.lifecycle.LiveData
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class PriceViewModel : BaseKotlinViewModel(){

    private val TAG = javaClass.simpleName
    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    fun clickNextButton() {
        _startNextActivityEvent.call()
    }
}