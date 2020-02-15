package com.nexters.yetda.android.model

data class QuestionModel(
    /**
     * 초기화를 하지 않는 경우 FB의 스냅샷을 모델에 넣을 때
     * "RuntimeException: Could not deserialize object"가 나타남
     */
    var id: Int = 0,
    var question: String = "",
    var tag: String = ""
//    var tagModels: ArrayList<Tag> = tags.map {
//        convertStringToTag(it)
//    }
)
