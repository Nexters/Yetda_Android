package com.nexters.yetda.android.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm

class QuestionViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private val realm by lazy {
        Realm.getDefaultInstance()
    }

        private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent

    private val _getQuestionEvent = SingleLiveEvent<Question>()
    val getQustionEvent: LiveData<Question>
        get() = _getQuestionEvent

    var name = MutableLiveData<String>()

    fun findQuestion(tags: ArrayList<String>) {
        val question= QuestionDao(realm).findQuestion(tags)
        _getQuestionEvent.postValue(question)
    }

    fun clickNoButton() {
        //
    }

    fun clickOkButton() {
        //
    }

    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }
}