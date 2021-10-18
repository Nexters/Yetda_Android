package com.nexters.yetda.android.domain.database

import android.os.Parcel
import android.os.Parcelable
import com.nexters.yetda.android.domain.database.model.Present
import com.nexters.yetda.android.domain.database.model.Tag
import com.nexters.yetda.android.util.LiveRealmData
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmResults
import kotlinx.parcelize.Parceler

class RealmUtil {

    companion object {

        fun personModel(realm: Realm): PersonDao = PersonDao(realm)

        fun <T : RealmModel> asLiveData(realmResults: RealmResults<T>) = LiveRealmData(realmResults)


        //RealmList read&write function
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
            writeInt(
                when {
                    realmList == null -> 0
                    else -> 1
                }
            )
            if (realmList != null) {
                writeInt(realmList.size)
                for (t in realmList) {
                    writeParcelable(t, 0)
                }
            }
        }
    }


    // Interface for Parcel
    // RealmList용 Parceler 기본 오버라이드 메소드 생성
    interface RealmListParceler<T> : Parceler<RealmList<T>?> where T : RealmModel, T : Parcelable {
        override fun create(parcel: Parcel): RealmList<T>? = parcel.readRealmList(clazz)

        override fun RealmList<T>?.write(parcel: Parcel, flags: Int) {
            parcel.writeRealmList(this, clazz)
        }

        val clazz: Class<T>
    }

    // Parcelable RealmList를 참조하는 Present 객체 생성
    object PresentRealmListParceler : RealmListParceler<Present> {
        override val clazz: Class<Present>
            get() = Present::class.java
    }

    object TagRealmListParceler : RealmListParceler<Tag> {
        override val clazz: Class<Tag>
            get() = Tag::class.java
    }
}
