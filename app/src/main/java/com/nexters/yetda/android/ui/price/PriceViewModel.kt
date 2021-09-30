package com.nexters.yetda.android.ui.price

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.util.SingleLiveEvent


class PriceViewModel : ViewModel() {

    private val TAG = javaClass.simpleName

    var btnActivated = MutableLiveData<Boolean>(false)
    var gender = ""
    var birthday = ""

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
        // TODO: null처리
        gender = intent.getStringExtra("GENDER") ?: ""
        birthday = intent.getStringExtra("BIRTHDAY") ?: ""

        val history = History()
        history.name = intent.getStringExtra("NAME") ?: ""
        history.gender = gender
        history.birthday = birthday

        return history

    }

    fun getTags(): ArrayList<String> {
        val list = ArrayList<String>()
        //1. Reverse gender
        var _gender = ""
        _gender = if (gender == "여성") {
            "남성"
        } else {
            "여성"
        }
        list.add(_gender)

        //2. get season and reverse
        var season = birthday.substring(0, 2)
        val summer = arrayOf(6, 7, 8, 9)
        val winter = arrayOf(1, 2, 11, 12)

        if (season.toInt() in summer) {
            season = "겨울"
        } else if (season.toInt() in winter) {
            season = "여름"
        }
        list.add(season)

//        return arrayOf(_gender, season)
        return list
    }
}