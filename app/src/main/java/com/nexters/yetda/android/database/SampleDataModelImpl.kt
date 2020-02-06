package com.nexters.yetda.android.database

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class SampleDataModelImpl(private var realm: Realm): SampleDataModel {

    override fun setSampleData() {
        var size = realm.where<Person>().count()
        realm.executeTransaction { realm ->
            // Add a person
            val person = realm.createObject<Person>()
            person.id = size
            person.name = "Person_$size"
            person.age = (size + 10).toInt()
        }

    }

}