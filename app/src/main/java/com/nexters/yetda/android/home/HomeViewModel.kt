package com.nexters.yetda.android.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Tag
import com.nexters.yetda.android.database.model.Update
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.model.UpdateModel
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private val db = FirebaseFirestore.getInstance()
    private var realm = Realm.getDefaultInstance()

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
//                    it.deleteAll()
                    realm.where<Present>().findAll().deleteAllFromRealm()
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
}