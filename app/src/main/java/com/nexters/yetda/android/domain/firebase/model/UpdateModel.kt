package com.nexters.yetda.android.domain.firebase.model

import com.google.firebase.Timestamp

data class UpdateModel(
    var updated_at: Timestamp = Timestamp.now()
)