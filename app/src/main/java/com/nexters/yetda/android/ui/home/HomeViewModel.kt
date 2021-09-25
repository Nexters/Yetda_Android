package com.nexters.yetda.android.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.dao.UpdateDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.model.QuestionModel
import com.nexters.yetda.android.model.UpdateModel
import com.nexters.yetda.android.util.FireInTheRealm
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

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
        val fireInTheRealm: FireInTheRealm? = null
        fireInTheRealm?.test(db, realm)
//        FireInTheRealm.test(db, realm)

        /*db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)

                PresentDao(realm).deleteAll()
                for ((i, doc) in documents.withIndex()) {
                    val tags = ArrayList<Tag>()
                    for (tagString in doc.tags) {
                        val tag = Tag()
                        tag.tag = tagString
                        tags.add(tag)
                    }
                    PresentDao(realm).addPresent(doc.id, doc.name, doc.price, doc.image, tags)
                }
                //TODO:Sample Input, relase시 삭제
//                sampleHistory()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/
    }

    fun getQuestionsList() {
        db.collection("question")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(QuestionModel::class.java)
                QuestionDao(realm).deleteAll()
                for (doc in documents) {
                    QuestionDao(realm).addQuestion(doc.id, doc.question, doc.tag)
                }
                getPresentsList()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getUpdatesInfo() {
        db.collection("updates")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(UpdateModel::class.java)

                //newest updateDate
                val strDate = documents[0].updated_at.seconds
                if (UpdateDao(realm).findUpdate()?.updatedAt != strDate) {
                    //NEED Update
                    UpdateDao(realm).addUpdate(strDate)
                    getQuestionsList()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
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