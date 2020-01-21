package com.nexters.yetda.android.name

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.util.SingleLiveEvent


class NameViewModel : BaseKotlinViewModel(), NameContact.ViewModel {

    private val TAG = javaClass.simpleName

    var name = MutableLiveData<String>()
    var btnActivated = MutableLiveData<Boolean>()

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent


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

    //클릭 이벤트를 받아온다.
    fun clickNextButton() {
        //null처리 어떻게하더라.. 내일 마저 고고
        if(btnActivated.value!= null && btnActivated.value!!)
            _startNextActivityEvent.call()
    }
    fun afterTextChanged(s: Editable?) {
        btnActivated.value = !name.value.equals("")
    }
}