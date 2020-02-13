package com.nexters.yetda.android.gender

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class GenderViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    var btnActivated = MutableLiveData<Boolean>(true)
    var isfemale = MutableLiveData<Boolean>(true)
    var ismale = MutableLiveData<Boolean>(false)

    var name = MutableLiveData<String>()

    init {
        name.value = "__"
    }

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent
    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent

    //클릭 이벤트를 받아온다.
    fun clickNextButton() {
        if (btnActivated.value!!)
            _startNextActivityEvent.call()
    }

    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }

    fun isFemale() {
        Log.e(TAG, "isFemale Click")
        if (!isfemale.value!!) {
            isfemale.value = !isfemale.value!!
            ismale.value = false
        }
    }

    fun isMale() {
        Log.e(TAG, "isMale Click")
        if (!ismale.value!!) {
            ismale.value = !ismale.value!!
            isfemale.value = false
        }
    }

    fun getGender(): String {
        return if (isfemale.value!!) "F"
        else "M"
    }

    fun getUserFromIntent(intent: Intent) {
        name.value = intent.getStringExtra("NAME")
    }
}