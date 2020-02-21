package com.nexters.yetda.android.database.dao

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.auth.User
import com.nexters.yetda.android.database.RealmUtil.Companion.asLiveData
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.util.LiveRealmData
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class HistoryDao(private val mRealm: Realm) {

    fun findAllHistory(): LiveData<RealmResults<History>> {
        return asLiveData(
            mRealm.where<History>(History::class.java)
                .findAllAsync()
        )
    }

    fun findHistoryById(id: Int): History? {
        return mRealm.where<History>()
            .equalTo("id",id)
            .findFirst()
    }

    fun findHistory(): History? {
        return mRealm.where<History>()
            .findFirst()
    }

    fun deleteAll(){
        val result = mRealm.where<History>().findAll()
        mRealm.executeTransaction {
            result.deleteAllFromRealm()
        }
    }
    fun addHistory(
        name: String,
        gender: String,
        birthday: String,
        startPrice: Long,
        endPrice: Long,
        presents: RealmList<Present>
    ) {
        mRealm.executeTransaction {
            val currentId = mRealm.where<History>(History::class.java).max("id")
            val nextId = if (currentId == null || currentId == 0) 1 else currentId.toInt() + 1

            val history = mRealm.createObject<History>(nextId)
            history.name = name
            history.gender = gender
            history.birthday = birthday
            history.startPrice = startPrice
            history.endPrice = endPrice
            history.presents = presents
            history.createdAt = System.currentTimeMillis() / 1000

            it.copyToRealm(history)
        }

    }

}