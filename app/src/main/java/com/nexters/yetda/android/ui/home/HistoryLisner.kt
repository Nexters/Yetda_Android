package com.nexters.yetda.android.ui.home

import com.nexters.yetda.android.domain.database.model.History

public interface HistoryLisner {
    fun onDelClick(item: History);
}