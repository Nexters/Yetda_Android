package com.nexters.yetda.android.detail


import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.util.SingleLiveEvent


class DetailViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName


    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent


    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }

}