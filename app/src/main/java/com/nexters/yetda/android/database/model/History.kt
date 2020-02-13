package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class History : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var gender:String=""
    var birthday: String = ""
    var startPrice: Int = 0
    var endPrice: Int = 0
    var presents = RealmList<Present>()

    var createdAt: Date? = null

}