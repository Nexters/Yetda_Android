package com.nexters.yetda.android.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nexters.yetda.android.domain.database.dao.HistoryDao
import com.nexters.yetda.android.domain.database.dao.QuestionDao
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.domain.firebase.FireInTheRealm
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {

    private val TAG = javaClass.simpleName

    private val db = Firebase.firestore
    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    private val fireInTheRealm by lazy {
        FireInTheRealm(db, realm)
    }

    var isEmptyList = MutableLiveData<Boolean>(false)

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    fun clickNextButton() {
        _startNextActivityEvent.call()
        Log.e(TAG, HistoryDao(realm).findHistory().toString())
    }

    fun getPresentsList() {
        fireInTheRealm.getPresents()
    }

    fun getQuestionsList() {
        fireInTheRealm.getQuestionsList(onSuccess = { getPresentsList() })
    }

    fun getUpdatesInfo() {
        fireInTheRealm.getUpdatesInfo(onSuccess = { getQuestionsList() })
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time * 1000)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(date)
    }

    fun getAllHistory(): LiveData<RealmResults<History>> {
        return HistoryDao(realm).findAllHistory()
    }

    fun deleteById(id: Int){
        HistoryDao(realm).deleteById(id)
    }

    fun sampleHistory() {
        //TODO: Present가 비어있을 경우 처리해야함.
//        var presents = RealmList<Present>()
//        presents.add(PresentDao(realm).findPresentById(13))
//        presents.add(PresentDao(realm).findPresentById(12))
//        HistoryDao(realm).addHistory("도현", "여성", "3월 9일", 10000, 20000, presents)
//        HistoryDao(realm).addHistory("쭈피", "여성", "3월 9일", 10000, 20000, presents)
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    fun initAskedStatus() {
        QuestionDao(realm).initAskedStatus()
    }
}