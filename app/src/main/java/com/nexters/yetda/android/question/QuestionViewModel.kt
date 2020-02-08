package com.nexters.yetda.android.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.base.BaseKotlinViewModel
import com.nexters.yetda.android.database.Person
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.util.SingleLiveEvent
import io.realm.Realm
import io.realm.kotlin.createObject


class QuestionViewModel : BaseKotlinViewModel() {

    private val TAG = javaClass.simpleName
    private lateinit var mDb: Realm

    private val _startNextActivityEvent = SingleLiveEvent<Any>()
    val startNextActivityEvent: LiveData<Any>
        get() = _startNextActivityEvent

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
        RealmUtil.personModel(mDb).addPerson()
//            .findLoansByNameAfter("Mike", getYesterdayDate())
//        mLoansResult = Transformations.map<RealmResults<Loan>, Any>(
//            loans,
//            object : Function<RealmResults<Loan?>?, String?>() {
//                fun apply(loans: RealmResults<Loan>): String? {
//                    val sb = StringBuilder()
//                    val simpleDateFormat = SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm",
//                        Locale.US
//                    )
//                    for (loan in loans) {
//                        sb.append(
//                            java.lang.String.format(
//                                "%s\n  (Returned: %s)\n",
//                                loan.book.title,
//                                simpleDateFormat.format(loan.endTime)
//                            )
//                        )
//                    }
//                    return sb.toString()
//                }
//            })
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
}