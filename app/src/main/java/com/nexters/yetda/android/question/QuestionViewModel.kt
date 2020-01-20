package com.nexters.yetda.android.question

import android.util.Log
import androidx.lifecycle.LiveData
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class QuestionViewModel : BaseKotlinViewModel(){

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    private val TAG = javaClass.simpleName

    fun clickNextButton() {
        _startNextActivityEvent.call()
    }
}