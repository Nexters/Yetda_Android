package com.nexters.yetda.android.database

import com.nexters.yetda.android.util.LiveRealmData
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

class RealmUtil {

    companion object {

        fun personModel(realm: Realm): PersonDao = PersonDao(realm)

        fun <T : RealmModel> asLiveData(realmResults: RealmResults<T>) = LiveRealmData(realmResults)
    }

}
