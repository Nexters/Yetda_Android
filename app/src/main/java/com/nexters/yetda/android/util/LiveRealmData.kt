package com.nexters.yetda.android.util

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults


class LiveRealmData<T : RealmModel>(realmResults: RealmResults<T>) : LiveData<RealmResults<T>>() {

    private var results: RealmResults<T>? = realmResults
    private val listener: RealmChangeListener<RealmResults<T>?> =
        RealmChangeListener { results -> setValue(results) }

    override fun onActive() {
        super.onActive()
        results?.addChangeListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        results?.removeChangeListener(listener)
    }
}