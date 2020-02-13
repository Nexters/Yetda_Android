package com.nexters.yetda.android.home

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.nexters.yetda.android.base.BaseViewModel
import com.nexters.yetda.android.model.PresentModel
import com.nexters.yetda.android.util.SingleLiveEvent


class HomeViewModel : BaseViewModel() {

    private val TAG = javaClass.simpleName

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


    fun clickNextButton() {
        Log.d(TAG, "클릭을하면 이곳으로 옵니다.")
        _startNextActivityEvent.call()
    }

    fun getPresentsList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)
                Log.d(TAG, "* * * ${documents[0]}")
                for (document in documentSnapshot) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    Log.d(TAG, "* * * ${document}")
//                    name.value = document.data["name"]?.toString()
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