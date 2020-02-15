package com.nexters.yetda.android.database.dao

import androidx.lifecycle.LiveData
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.database.model.Update
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class PresentDao(private val mRealm: Realm) {

    fun findAllPresents(): LiveData<RealmResults<Present>> {
        return RealmUtil.asLiveData(
            mRealm.where<Present>(Present::class.java)
                .findAllAsync()
        )
    }

    fun findPresentById(id: Int): Present? {
        return mRealm.where<Present>()
            .equalTo("id",id)
            .findFirst()

    }

    fun findPresent():Present?{
        return mRealm.where<Present>()
            .findFirst()
    }
    fun addPresent(
        _id:Int,
        _name:String,
        _price:Long,
        _tags:ArrayList<String>
    ) {
        mRealm.executeTransaction {
            val item = mRealm.createObject<Present>(_id)
            item.name=_name
            item.price = _price
            item.tags.addAll(_tags)
            it.copyToRealm(item)
        }
    }
}