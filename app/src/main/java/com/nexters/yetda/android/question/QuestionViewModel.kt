package com.nexters.yetda.android.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.dao.PresentDao
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmList
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class QuestionViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private val realm by lazy {
        Realm.getDefaultInstance()
    }
    var qCount = 1 // 시작하자마자 질문이 1개 나타나기 때문
    var historyId = -1
    var history = History()

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent

    private val _getQuestionEvent = SingleLiveEvent<Question>()
    val getQustionEvent: LiveData<Question>
        get() = _getQuestionEvent


    private val _getQuestionFinished = SingleLiveEvent<Any>()
    val getQuestionFinished: LiveData<Any>
        get() = _getQuestionFinished


    var name = MutableLiveData<String>()
    var resultPresents = RealmList<Present>()

    fun findQuestion(tags: ArrayList<String>) {
        val question = QuestionDao(realm).findQuestion(tags)
        if (qCount < 6) {
            qCount += 1
            _getQuestionEvent.postValue(question)
        } else {
            // 6번째 질문이 나오지 않고 결과 화면이 출력
            _getQuestionFinished.call()
        }
    }

    fun saveHistoryInfo(history: History) {
        this.history = history
        Log.d(TAG, "* * * history ::: ${this.history}")

        HistoryDao(realm).findAllHistory()
    }

    fun findPresents(
        tags: ArrayList<String>,
        _startPrice: Long,
        _endPrice: Long
    ): ArrayList<Present> {
        val presents = PresentDao(realm).findPresents(tags, _startPrice, _endPrice)
        val presentList = ArrayList<Present>()
        presentList.addAll(presents)
        resultPresents.clear()
        resultPresents.addAll(presents.subList(0, presents.size))
        presentList.forEach {
            // todo : price값이 모두 0으로 들어가고 있음.
//            Log.d(TAG, "* * * p list ::: ${it.name} // ${it.price}")
        }
        return presentList
    }

    fun showResult() {

        //shuffle return presents
        val indices: ArrayList<Int> = ArrayList(resultPresents.size)
        for (i in 0 until resultPresents.size) {
            indices.add(i)
        }
        indices.shuffle()
        var max = if (resultPresents.size > 10)
            9
        else
            resultPresents.size - 1

        for (i in 0..max) {
            val index = indices[i]
            history.presents.add(resultPresents[index])
        }

    }

    fun addHistory() {
        historyId = HistoryDao(realm).addHistory(history)
        Log.d(TAG, "* * * id :: ${historyId}")
//        _startNextActivityEvent.call()
    }


    fun clickBackButton() {
        _backBeforeActivityEvent.call()
    }

    fun initAskedStatus() {
        QuestionDao(realm).initAskedStatus()
    }
}