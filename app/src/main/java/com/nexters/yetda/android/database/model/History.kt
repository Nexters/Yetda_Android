package com.nexters.yetda.android.database.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.*
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.parcel.WriteWith

/**
 * 1. putExtra
 *  intent.putExtra("TT", history)
 * 2. getExtra
 *  intent.getParcelableExtra("ITEM")
 */
@Parcelize
open class History(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var gender: String = "",
    var birthday: String = "",
    var startPrice: Long = 0,
    var endPrice: Long = 0,
    var presents: @WriteWith<PresentRealmListParceler> RealmList<Present> = RealmList(),
    var createdAt: Long = 0

): RealmObject(), Parcelable


object PresentRealmListParceler: RealmListParceler<Present> {
    override val clazz: Class<Present>
        get() = Present::class.java
}

interface RealmListParceler<T>: Parceler<RealmList<T>?> where T: RealmModel, T: Parcelable {
    override fun create(parcel: Parcel): RealmList<T>? = parcel.readRealmList(clazz)

    override fun RealmList<T>?.write(parcel: Parcel, flags: Int) {
        parcel.writeRealmList(this, clazz)
    }

    val clazz : Class<T>
}

fun <T> Parcel.readRealmList(clazz: Class<T>): RealmList<T>?
        where T : RealmModel,
              T : Parcelable = when {
    readInt() > 0 -> RealmList<T>().also { list ->
        repeat(readInt()) {
            list.add(readParcelable(clazz.classLoader))
        }
    }
    else -> null
}

fun <T> Parcel.writeRealmList(realmList: RealmList<T>?, clazz: Class<T>)
        where T : RealmModel,
              T : Parcelable {
    writeInt(when {
        realmList == null -> 0
        else -> 1
    })
    if (realmList != null) {
        writeInt(realmList.size)
        for (t in realmList) {
            writeParcelable(t, 0)
        }
    }
}