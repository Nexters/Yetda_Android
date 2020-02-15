package com.nexters.yetda.android.home

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.dao.HistoryDao
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.model.QuestionModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.RealmResults

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
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

//    fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
//        val result = arrayListOf<R>()
//        for (item in this) {
//            result.add(transform(item))
//        }
//        return result
//    }

    fun getPresentsList() {
        val db = FirebaseFirestore.getInstance()

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

        /*
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .addSnapshotListener { p0, p1 ->
                //
            }
        */
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

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}