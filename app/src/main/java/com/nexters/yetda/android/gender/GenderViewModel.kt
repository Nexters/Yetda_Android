package com.nexters.yetda.android.gender

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class GenderViewModel : BaseKotlinViewModel() {

    private val TAG = javaClass.simpleName

    var btnActivated = MutableLiveData<Boolean>(true)
    var isfemale = MutableLiveData<Boolean>(true)
    var ismale = MutableLiveData<Boolean>(false)

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    //클릭 이벤트를 받아온다.
    fun clickNextButton() {
        if (btnActivated.value!!)
            _startNextActivityEvent.call()
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
}