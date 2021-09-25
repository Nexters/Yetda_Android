package com.nexters.yetda.android.domain.database.model

import android.os.Parcelable
import com.nexters.yetda.android.domain.database.RealmUtil
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

/**
 * 1. putExtra
 *  intent.putExtra("TT", history)
 * 2. getExtra
 *  intent.getParcelableExtra("ITEM")
 */
// 참고 : 초기화 형식이 아닌 그냥 클래스로 RealmObject 생성 시 정상적으로 넘어가지 않는다(이유를 모르겠음..)
// Intent시 Realm객체를 넘기기 위해서는 parcelable 해야한다.
// Realm에서 안내하는 Parcel 라이브러리가 있지만 코틀린 Extension의 실험모드로 Parcelize를 사용할 경우 더 간단하게 적용 가능하다.
// 단 Parcelize에서 RealmList를 지원하지 않기 때문에 별도로 적용해 주어야 한다.(=@WriteWith)
@Parcelize
open class History(
    @PrimaryKey
    var id: Int = 1,
    var name: String = "",
    var gender: String = "",
    var birthday: String = "",
    var startPrice: Long = 0,
    var endPrice: Long = 0,
    var presents: @WriteWith<RealmUtil.PresentRealmListParceler> RealmList<Present> = RealmList(),
    var createdAt: Long = 0
): RealmObject(), Parcelable {
    override fun toString(): String {
        return "History(id=$id, name='$name', gender='$gender', birthday='$birthday', startPrice=$startPrice, endPrice=$endPrice, presents=$presents, createdAt=$createdAt)"
    }
}