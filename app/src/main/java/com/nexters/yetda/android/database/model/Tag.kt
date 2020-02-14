package com.nexters.yetda.android.database.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Tag : RealmObject() {
    @PrimaryKey
    var name: String = ""
}