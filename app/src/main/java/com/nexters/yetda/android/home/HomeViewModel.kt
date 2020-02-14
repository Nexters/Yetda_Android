package com.nexters.yetda.android.home

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Tag
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm

class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private var realm = Realm.getDefaultInstance()

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

        ArrayList<String>().map {
            val tag = Tag()
            tag.name = it
            return@map tag
        }
        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                realm.executeTransaction {
                    it.deleteAll()
                    for ((i, doc) in documents.withIndex()) {
                        val present = it.createObject(Present::class.java, i)
                        present.name = doc.name
                        present.price = doc.price

                        // todo 더 예쁜 방법이 없을까??
                        val tags = ArrayList<Tag>()
                        for (tagString in doc.tags) {
                            val tag = Tag()
                            tag.name = tagString
                            tags.add(tag)
                        }
                        present.tags.addAll(tags)
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
}