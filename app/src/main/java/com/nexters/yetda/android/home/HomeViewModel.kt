package com.nexters.yetda.android.home

import android.transition.Visibility
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.dao.PresentDao
import com.nexters.yetda.android.database.dao.QuestionDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.model.QuestionModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private val realm =
        Realm.getDefaultInstance()


    val db = FirebaseFirestore.getInstance()

    var isEmptyList = MutableLiveData<Boolean>(false)

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    fun clickNextButton() {
        _startNextActivityEvent.call()
        Log.e(TAG,HistoryDao(realm).findHistory().toString())
    }


    fun getPresentsList() {

        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                realm.executeTransaction {
                    it.deleteAll()
                    for ((i, doc) in documents.withIndex()) {
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
            .addOnSuccessListener {
               sampleHistory()
            }
        /*
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .addSnapshotListener { p0, p1 ->
                //
            }
        */
    }

    fun getQuestionsList() {

        db.collection("question")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(QuestionModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                realm.executeTransactionAsync {
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
            .addOnSuccessListener {
                getQuestionsList()
            }

        /*
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .addSnapshotListener { p0, p1 ->
                //
            }
        */

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