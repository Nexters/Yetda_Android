package com.nexters.yetda.android.database.dao

import android.util.Log
import androidx.lifecycle.LiveData
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Tag
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class PresentDao(private val mRealm: Realm) {

    private val TAG = javaClass.simpleName

    fun findAllPresents(): LiveData<RealmResults<Present>> {
        return RealmUtil.asLiveData(
            mRealm.where<Present>(Present::class.java)
                .findAllAsync()
        )
    }

    fun findPresentById(id: Int): Present? {
        return mRealm.where<Present>()
            .equalTo("id", id)
            .findFirst()

    }

    fun findPresent(): Present? {
        return mRealm.where<Present>()
            .findFirst()
    }

    fun findPresents(
        tags: ArrayList<String>,
        _startPrice: Long,
        _endPrice: Long
    ): RealmResults<Present> {
        val tagList = arrayOfNulls<String>(tags.size)
        tags.toArray(tagList)
        Log.d(TAG, "* * * * ${_startPrice} // ${_endPrice}")
        return mRealm.where<Present>()
            .not()
            .beginGroup()
            .`in`("tags.tag", tagList)
            .endGroup()
            .between("price", _startPrice, _endPrice)
            .findAll()
    }


    fun deleteAll() {
        val result = mRealm.where<Present>().findAll()
        mRealm.executeTransaction {
            result.deleteAllFromRealm()
        }
    }

    fun addPresent(
        _id: Int,
        _name: String,
        _price: Long,
        _tags: ArrayList<Tag>
    ) {
        mRealm.executeTransaction {
            val item = mRealm.createObject<Present>(_id)
            item.name = _name
            item.price = _price
            item.tags.addAll(_tags)
            it.copyToRealm(item)
        }
    }

}