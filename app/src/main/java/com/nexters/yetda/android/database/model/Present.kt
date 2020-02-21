package com.nexters.yetda.android.database.model

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Present : RealmObject(), Parcelable {
    @PrimaryKey
    var id: Int = 0
    var price: Long = 0
    var name: String = ""
    var tags = RealmList<Tag>()
    var image: String = ""
}