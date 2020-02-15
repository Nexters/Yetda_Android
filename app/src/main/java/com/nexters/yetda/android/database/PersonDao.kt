package com.nexters.yetda.android.database

import android.util.Log
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class PersonDao(realm: Realm) {
    private val TAG = javaClass.simpleName
    private var mRealm = realm

    fun addPerson() {
        if (mRealm.isEmpty) {
            Log.d(TAG, "* * * PersonDao 1")
            return
        }
        Log.d(TAG, "* * * PersonDao 2")
        val size = mRealm.where<Person>().count()
        mRealm.executeTransaction { realm ->
            // Add a person
            val person = realm.createObject<Person>()
            person.id = size
            person.name = "Person_$size"
            person.age = (size + 10).toInt()
        }
    }

}