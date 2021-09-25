package com.nexters.yetda.android.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nexters.yetda.android.domain.database.dao.PresentDao
import com.nexters.yetda.android.domain.database.model.Tag
import com.nexters.yetda.android.domain.firebase.model.PresentModel
import io.realm.Realm
import java.util.*

class FireInTheRealm {

    companion object {

    }

    val TAG = javaClass.simpleName

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