package com.nexters.yetda.android.domain.database.model

import io.realm.RealmObject

open class Update: RealmObject() {
    var updatedAt: Long = 0
}