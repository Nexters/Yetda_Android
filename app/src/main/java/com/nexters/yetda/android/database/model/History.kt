package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class History : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var gender: String = ""
    var birthday: String = ""
    var startPrice: Long = 0
    var endPrice: Long = 0
    var presents = RealmList<Present>()

    var createdAt: Long = 0

}