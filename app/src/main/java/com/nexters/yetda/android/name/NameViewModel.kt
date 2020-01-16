package com.nexters.yetda.android.name

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseKotlinViewModel


class NameViewModel : BaseKotlinViewModel(), NameContact.ViewModel {

    private val TAG = "NameViewModel"

    var name = MutableLiveData<String>()

    override fun getFirebaseSampleData() {
        val db = FirebaseFirestore.getInstance()

        db.collection("presents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, document.data["name"].toString())

                    name.value = document.data["name"]?.toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


}