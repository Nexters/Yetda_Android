package com.nexters.yetda.android.model

import com.nexters.yetda.android.database.model.Tag

data class PresentModel(
    /**
     * 초기화를 하지 않는 경우 FB의 스냅샷을 모델에 넣을 때
     * "RuntimeException: Could not deserialize object"가 나타남
     */
    var name: String = "",
    var price: Long = 0,
    var tags: ArrayList<String> = ArrayList()
//    var tagModels: ArrayList<Tag> = tags.map {
//        convertStringToTag(it)
//    }
)

fun convertStringToTag(text: String): Tag {
    val tag = Tag()
    tag.name = text
    return tag
}