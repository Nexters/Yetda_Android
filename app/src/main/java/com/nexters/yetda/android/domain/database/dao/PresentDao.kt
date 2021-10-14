package com.nexters.yetda.android.domain.database.dao

import androidx.lifecycle.LiveData
import com.nexters.yetda.android.domain.database.RealmUtil
import com.nexters.yetda.android.domain.database.model.Present
import com.nexters.yetda.android.domain.database.model.Tag
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

    fun findPresents(
        tags: ArrayList<String>,
        _startPrice: Long,
        _endPrice: Long
    ): RealmResults<Present> {
        val tagList = arrayOfNulls<String>(tags.size)
        tags.toArray(tagList)
        return mRealm.where<Present>()
            .not()
            .beginGroup()
            .`in`("tags.tag", tagList)
            .endGroup()
            .between("price", _startPrice, _endPrice).or().equalTo("price", 0L)
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
        _image: String,
        _tags: ArrayList<Tag>
    ) {
        mRealm.executeTransaction {
            val currentId = it.where<Present>(Present::class.java).max("id")
            val nextId = if (currentId == null || currentId == 0) 1 else currentId.toInt() + 1

            val item = it.createObject<Present>(nextId)
            item.name = _name
            item.price = _price
            item.image = _image
            item.tags.addAll(_tags)
            it.copyToRealm(item)
        }
    }

}