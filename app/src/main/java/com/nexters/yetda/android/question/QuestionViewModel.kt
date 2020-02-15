package com.nexters.yetda.android.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.database.Person
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.kotlin.createObject

class QuestionViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName
    private lateinit var mDb: Realm

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

    private val _backBeforeActivityEvent = SingleLiveEvent<Any>()
    val backBeforeActivityEvent: LiveData<Any>
        get() = _backBeforeActivityEvent

    var name = MutableLiveData<String>()


    fun addPerson() {
        Log.d(TAG, "* * * QuestionViewModel 1")
        mDb = Realm.getDefaultInstance()
        Log.d(TAG, "* * * QuestionViewModel 2")
        if (mDb.isEmpty) {
            Log.d(TAG, "* * * QuestionViewModel 3")
//            mDb.beginTransaction()
//            mDb.createObject<Person>() // Create managed objects directly
//            mDb.commitTransaction()
        }

        // todo 확실히 필요없다고 생각되면 지우자
//        RealmUtil.personModel(mDb).addPerson()
    }

    fun clickNextButton() {
        val db = FirebaseFirestore.getInstance()

        db.collection("presents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, document.data["name"].toString())
                    name.value = document.data["name"]?.toString()
                    _startNextActivityEvent.value = document.data["name"]?.toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        _startNextActivityEvent.call()
    }

    fun clickBackButton(){
        _backBeforeActivityEvent.call()
    }
}