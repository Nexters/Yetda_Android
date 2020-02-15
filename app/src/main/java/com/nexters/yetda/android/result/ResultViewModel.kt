package com.nexters.yetda.android.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class ResultViewModel : BaseViewModel(){

    private val TAG = javaClass.simpleName

    var name = MutableLiveData<String>()

    init {
        name.value = "__"
    }


    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    fun clickNextButton() {
        _startNextActivityEvent.call()
    }
}