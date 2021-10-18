package com.nexters.yetda.android.domain.database.model

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.parcelize.Parcelize


@Parcelize
open class Tag(
    var tag: String = ""
) : RealmObject(), Parcelable

