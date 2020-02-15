package com.nexters.yetda.android.home

import android.transition.Visibility
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.dao.PresentDao
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.dao.UpdateDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.database.model.Update
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.model.QuestionModel
import com.nexters.yetda.android.model.UpdateModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    private var isNew = false
    private val db = FirebaseFirestore.getInstance()
    private val realm by lazy {
        Realm.getDefaultInstance()
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
        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                for ((i, doc) in documents.withIndex())
                    PresentDao(realm).addPresent(doc.id, doc.name, doc.price, doc.tags)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .addOnSuccessListener {
                sampleHistory()
            }
    }

    fun getQuestionsList() {

        db.collection("question")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(QuestionModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                for ((i, doc) in documents.withIndex()) {
                    QuestionDao(realm).addQuestion(doc.id, doc.question, doc.tag)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .addOnSuccessListener {
                getPresentsList()
            }
    }

    fun getUpdatesInfo() {
        db.collection("updates")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(UpdateModel::class.java)
//                Log.d(
//                    TAG,
//                    "* * * date : ${convertLongToTime(documents[0].updatedAt.seconds)}"
//                )//2020.02.15 14:39:52
//                Log.d(TAG, "* * * date : ${documents[0].updatedAt.seconds}") //1581777592

                //newest updateDate
                val strDate = documents[0].updated_at.seconds
                if (UpdateDao(realm).findUpdate()?.updatedAt != strDate) {
                    //NEED Update
                    UpdateDao(realm).addUpdate(strDate)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .addOnSuccessListener {
                getQuestionsList()
            }
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

    fun sampleHistory() {

        //TODO: Present가 비어있을 경우 처리해야함.
        var presents = RealmList<Present>()
        presents.add(PresentDao(realm).findPresentById(3))
        presents.add(PresentDao(realm).findPresentById(4))
        HistoryDao(realm).addHistory("도현", "여성", "3월 9일", 10000, 20000, presents)
        HistoryDao(realm).addHistory("쭈피", "여성", "3월 9일", 10000, 20000, presents)

    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}