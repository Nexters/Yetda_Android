package com.nexters.yetda.android.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.domain.database.dao.HistoryDao
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm

class ResultViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    var name = MutableLiveData<String>()

    init {
        name.value = "__"
    }

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    fun clickNextButton() {
        _startNextActivityEvent.call()
    }

    fun findHistoryById(id: Int): History {
        val history = HistoryDao(realm).findHistoryById(id)
        name.value = history?.name
        return history!!
    }

}