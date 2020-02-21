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
    var qCount = 1 // 시작하자마자 질문이 1개 나타나기 때문

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
        if (qCount < 6) {
            qCount += 1
            _getQuestionEvent.postValue(question)
        } else {
            // 6번째 질문이 나오지 않고 결과 화면이 출력
            showResult()
        }
    }

    fun findPresents(
        tags: ArrayList<String>,
        _startPrice: Long,
        _endPrice: Long
    ): ArrayList<Present> {
        val presents = PresentDao(realm).findPresents(tags, _startPrice, _endPrice)
        val presentList = ArrayList<Present>()
        presentList.addAll(realm.copyFromRealm(presents))
        presentList.forEach {
            // todo : price값이 모두 0으로 들어가고 있음.
            Log.d(TAG, "* * * p list ::: ${it.name} // ${it.price}")
        }
        return presentList
    }

    fun showResult() {
        _startNextActivityEvent.call()
    }

    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }
}