package com.nexters.yetda.android.ui.price

import android.content.Context
import androidx.databinding.BindingAdapter
import com.appyvet.materialrangebar.RangeBar


class MyRangeBar(context: Context?) : RangeBar(context) {

    @BindingAdapter("onRangeChanged")
    open fun setOnRangeBarChangeListener(
        view: RangeBar,
        listener: RangeBar.OnRangeBarChangeListener
    ): Unit {
        view.setOnRangeBarChangeListener(listener)
    }
}