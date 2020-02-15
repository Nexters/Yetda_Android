package com.nexters.yetda.android.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.database.model.Update
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.model.QuestionModel
import com.nexters.yetda.android.model.UpdateModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private val db = FirebaseFirestore.getInstance()
    private val realm by lazy {
        Realm.getDefaultInstance()
    }

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    fun clickNextButton() {
        Log.d(TAG, "클릭을하면 이곳으로 옵니다.")
        _startNextActivityEvent.call()
    }

    fun getPresentsList() {
        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                realm.executeTransaction {
                    realm.where<Present>().findAll().deleteAllFromRealm()
                    for (doc in documents) {
                        val present = it.createObject(Present::class.java, doc.id)
                        present.name = doc.name
                        present.price = doc.price
                        present.tags.addAll(doc.tags)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getQuestionsList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("question")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(QuestionModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                realm.executeTransaction {
                    it.deleteAll()
                    for ((i, doc) in documents.withIndex()) {
                        val question = it.createObject(Question::class.java, doc.id)
                        question.question = doc.question
                        question.tag = doc.tag
                    }
                }

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
                Log.d(TAG, "* * * date : ${convertLongToTime(documents[0].updatedAt.seconds)}")
                Log.d(TAG, "* * * date : ${documents[0].updatedAt.seconds}")
                realm.where<Update>().findAll().deleteAllFromRealm()
                realm.executeTransaction {
                    realm.where<Update>().findAll().deleteAllFromRealm()
                    for ((i, doc) in documents.withIndex()) {
//                        val update = it.createObject(Update::class.java)
                    }
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

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}