package com.nexters.yetda.android.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.PresentDao
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults

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
        val question = QuestionDao(realm).findQuestion(tags)
        _getQuestionEvent.postValue(question)
    }

    fun findPresents(
        tags: ArrayList<String>,
        _startPrice: Long,
        _endPrice: Long
    ): RealmResults<Present> {


        val presents = PresentDao(realm).findPresents(tags, _startPrice, _endPrice)
        Log.d(TAG, "* * * size :: ${presents.size}")
        val presentList = ArrayList<Present>()
        presentList.addAll(realm.copyFromRealm(presents))
        presentList.forEach {
            // todo : price값이 모두 0으로 들어가고 있음.
            Log.d(TAG, "* * * p list ::: ${it.name} // ${it.price}")
        }
        return presents
    }

    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }

    fun initAskedStatus(){
        QuestionDao(realm).initAskedStatus()
    }
}