package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class Present : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var startPrice: Int = 0
    var endPrice: Int = 0
    var tags = RealmList<Tag>()

    var createdAt: Date? = null

}