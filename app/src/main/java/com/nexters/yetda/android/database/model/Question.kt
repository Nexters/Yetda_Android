package com.nexters.yetda.android.database.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Question : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var question: String = ""
    var tag: Tag? = null
    var isAsked: Boolean = false

}