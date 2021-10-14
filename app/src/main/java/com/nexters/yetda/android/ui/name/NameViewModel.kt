package com.nexters.yetda.android.ui.name

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class NameViewModel : ViewModel() {

    private val TAG = javaClass.simpleName

    var name = MutableLiveData<String>("")
    var btnActivated = MutableLiveData<Boolean>(false)

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

    fun afterTextChanged(s: Editable?) {
        btnActivated.value = !name.value.equals("")
    }
}