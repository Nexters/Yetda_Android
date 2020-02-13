package com.nexters.yetda.android.model

data class PresentModel(
    var name: String = "",
    var price: Long = 0,
    var tags: ArrayList<String> = ArrayList()
)