package com.nexters.yetda.android.ui.birthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class BirthdayViewModel : ViewModel() {

    private val TAG = javaClass.simpleName
    var btnActivated = MutableLiveData<Boolean>(false)
    var m1 = MutableLiveData<String>("")
    var m2 = MutableLiveData<String>("")
    var d1 = MutableLiveData<String>("")
    var d2 = MutableLiveData<String>("")
    var name = MutableLiveData<String>()
    var gender: String = ""
    var birthday: String = ""

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

    fun validBirthday(isValid: Boolean) {
        btnActivated.value = isValid
        birthday = m1.value + m2.value + d1.value + d2.value
//        Log.e(TAG, "birthday is $birthday")
    }

    fun getUserFromIntent(args: BirthdayActivityArgs) {
        name.value = args.name
        gender = args.gender
    }
}