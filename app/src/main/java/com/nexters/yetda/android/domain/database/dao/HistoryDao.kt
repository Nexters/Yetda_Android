package com.nexters.yetda.android.domain.database.dao

import androidx.lifecycle.LiveData
import com.nexters.yetda.android.domain.database.RealmUtil.Companion.asLiveData
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.domain.database.model.Present
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class HistoryDao(private val mRealm: Realm) {

    private val TAG = javaClass.simpleName

    fun findAllHistory(): LiveData<RealmResults<History>> {
        return asLiveData(
            mRealm.where<History>(History::class.java)
                .sort("createdAt", Sort.DESCENDING)
                .findAllAsync()
        )
    }

    fun findHistoryById(id: Int): History? {
        return mRealm.where<History>()
            .equalTo("id", id)
            .findFirst()
    }

    fun findHistory(): History? {
        return mRealm.where<History>()
            .findFirst()
    }

    fun deleteHistory(id: Int) {
        val result = mRealm.where<History>()
            .equalTo("id", id)
            .findFirst()
        mRealm.executeTransaction {
            result?.deleteFromRealm()
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
            val currentId = it.where<History>(History::class.java).max("id")
            val nextId = if (currentId == null || currentId == 0) 1 else currentId.toInt() + 1

            val history = it.createObject<History>(nextId)
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

    fun addHistory(history: History): Int {
        val currentId = mRealm.where<History>(History::class.java).max("id")
        val nextId = if (currentId == null || currentId == 0) 1 else currentId.toInt() + 1

        mRealm.executeTransaction {
            val newHistory = it.createObject<History>(nextId)
            newHistory.name = history.name
            newHistory.gender = history.gender
            newHistory.birthday = history.birthday
            newHistory.startPrice = history.startPrice
            newHistory.endPrice = history.endPrice
            newHistory.presents = history.presents
            newHistory.createdAt = System.currentTimeMillis() / 1000

            it.copyToRealm(newHistory)

        }
        return nextId
    }
}