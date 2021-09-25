package com.nexters.yetda.android.domain.database.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Question : RealmObject() {
    @PrimaryKey
    var id: Int = 1
    var question: String = ""
    var tag: String = ""
    var isAsked: Boolean = false
}