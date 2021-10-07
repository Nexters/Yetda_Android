package com.nexters.yetda.android.domain.database.dao

import com.nexters.yetda.android.domain.database.model.Update
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class UpdateDao(private val mRealm: Realm) {

    fun findUpdate(): Update? {
        return mRealm.where<Update>()
            .sort("updatedAt", Sort.DESCENDING)
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