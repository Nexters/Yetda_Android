package com.nexters.yetda.android.birthday

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class BirthdayViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    var btnActivated = MutableLiveData<Boolean>(false)
    var m1 = MutableLiveData<String>("")
    var m2 = MutableLiveData<String>("")
    var d1 = MutableLiveData<String>("")
    var d2 = MutableLiveData<String>("")
    var name = MutableLiveData<String>()

    init {
        name.value = "쭈피"
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
    fun validBirthday(isValid: Boolean) {
        btnActivated.value = isValid
        var birthday = m1.value + m2.value + d1.value + d2.value
        Log.e(TAG, "birthday is $birthday")
    }

}