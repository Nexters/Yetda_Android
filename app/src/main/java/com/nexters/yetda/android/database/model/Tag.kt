package com.nexters.yetda.android.database.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
open class Tag(
    var tag: String = ""
) : RealmObject(), Parcelable

