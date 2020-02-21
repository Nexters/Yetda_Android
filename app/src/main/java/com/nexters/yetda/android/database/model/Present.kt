package com.nexters.yetda.android.database.model

import android.os.Parcelable
import com.nexters.yetda.android.database.RealmUtil
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

@Parcelize
open class Present(
    @PrimaryKey
    var id: Int = 1,
    var price: Long = 0,
    var name: String = "",
    var tags: @WriteWith<RealmUtil.TagRealmListParceler> RealmList<Tag> = RealmList(),
    var image: String = ""
) : RealmObject(), Parcelable

