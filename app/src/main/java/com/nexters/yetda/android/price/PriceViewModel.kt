package com.nexters.yetda.android.price

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.RealmObject


class PriceViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    var btnActivated = MutableLiveData<Boolean>(false)
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

    fun getUserFromIntent(intent: Intent): History {
        val history = History()
        name.value = intent.getStringExtra("NAME")
        history.name = name.value!!
        history.gender = intent.getStringExtra("GENDER")
        history.birthday = intent.getStringExtra("BIRTHDAY")
        return history
    }
}