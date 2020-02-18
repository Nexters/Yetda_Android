package com.nexters.yetda.android.database.dao

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.auth.User
import com.nexters.yetda.android.database.RealmUtil.Companion.asLiveData
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Update
import com.nexters.yetda.android.util.LiveRealmData
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class UpdateDao(private val mRealm: Realm) {

    fun findUpdate(): Update? {
        return mRealm.where<Update>()
            .sort("updatedAt",Sort.DESCENDING)
            .findFirst()
    }

    fun addUpdate(
        updateAt: Long
    ) {
        mRealm.executeTransaction {
            val update = mRealm.createObject<Update>()
            update.updatedAt = updateAt
            it.copyToRealm(update)
        }
    }
}