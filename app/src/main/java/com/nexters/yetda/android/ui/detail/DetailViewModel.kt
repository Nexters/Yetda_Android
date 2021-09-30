package com.nexters.yetda.android.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class DetailViewModel : ViewModel() {

    private val TAG = javaClass.simpleName


    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent


    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }

}