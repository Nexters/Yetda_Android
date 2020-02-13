package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Tag : RealmObject() {
    @PrimaryKey
    var name: String = ""

    var createdAt: Date? = null

}