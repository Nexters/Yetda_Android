package com.nexters.yetda.android.domain.database.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.internal.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
open class Tag(
    var tag: String = ""
) : RealmObject(), Parcelable

