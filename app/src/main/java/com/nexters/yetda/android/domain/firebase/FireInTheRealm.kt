package com.nexters.yetda.android.domain.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nexters.yetda.android.domain.database.dao.PresentDao
import com.nexters.yetda.android.domain.database.dao.QuestionDao
import com.nexters.yetda.android.domain.database.dao.UpdateDao
import com.nexters.yetda.android.domain.database.model.Tag
import com.nexters.yetda.android.domain.firebase.model.PresentModel
import com.nexters.yetda.android.domain.firebase.model.QuestionModel
import com.nexters.yetda.android.domain.firebase.model.UpdateModel
import io.realm.Realm
import java.util.*

class FireInTheRealm(private val db: FirebaseFirestore, private val realm: Realm) {

    val TAG = javaClass.simpleName

    fun getPresents(onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)

                PresentDao(realm).deleteAll()
                for ((i, doc) in documents.withIndex()) {
                    val tags = ArrayList<Tag>()
                    for (tagString in doc.tags) {
                        val tag = Tag()
                        tag.tag = tagString
                        tags.add(tag)
                    }
                    PresentDao(realm).addPresent(doc.id, doc.name, doc.price, doc.image, tags)
                }
                onSuccess?.invoke()
                //TODO:Sample Input, relase시 삭제
//                sampleHistory()
            }
            .addOnFailureListener { exception ->
                onFailure?.invoke()
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getUpdatesInfo(onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        db.collection("updates")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(UpdateModel::class.java)

                //newest updateDate
                val strDate = documents[0].updated_at.seconds
                if (UpdateDao(realm).findUpdate()?.updatedAt != strDate) {
                    //NEED Update
                    UpdateDao(realm).addUpdate(strDate)
                    onSuccess?.invoke()
                }
            }
            .addOnFailureListener { exception ->
                onFailure?.invoke()
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getQuestionsList(onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        db.collection("question")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(QuestionModel::class.java)
                QuestionDao(realm).deleteAll()
                for (doc in documents) {
                    QuestionDao(realm).addQuestion(doc.id, doc.question, doc.tag)
                }
                onSuccess?.invoke()
            }
            .addOnFailureListener { exception ->
                onFailure?.invoke()
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun test(db: FirebaseFirestore, realm: Realm) {
        db.collection("presents")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documents = documentSnapshot.toObjects(PresentModel::class.java)

                PresentDao(realm).deleteAll()
                for ((i, doc) in documents.withIndex()) {
                    val tags = ArrayList<Tag>()
                    for (tagString in doc.tags) {
                        val tag = Tag()
                        tag.tag = tagString
                        tags.add(tag)
                    }
                    PresentDao(realm).addPresent(doc.id, doc.name, doc.price, doc.image, tags)
                }
                //TODO:Sample Input, relase시 삭제
//                sampleHistory()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

}