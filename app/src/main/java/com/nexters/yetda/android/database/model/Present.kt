package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Present : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var price: Long = 0
    var name: String = ""
    var tags = RealmList<String>()
    var image: String = ""
}