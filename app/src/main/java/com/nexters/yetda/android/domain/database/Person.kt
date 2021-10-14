package com.nexters.yetda.android.domain.database


import io.realm.RealmModel
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Person : RealmModel {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var age: Int = 0

    @Ignore
    var tempReference: Int = 0
}