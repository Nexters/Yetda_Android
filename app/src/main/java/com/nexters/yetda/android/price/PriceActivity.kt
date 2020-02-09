package com.nexters.yetda.android.price

import android.content.Intent
import android.util.Log
import android.util.Range
import androidx.lifecycle.Observer
import com.appyvet.materialrangebar.RangeBar
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityPriceBinding
import com.nexters.yetda.android.question.QuestionActivity
import org.koin.android.viewmodel.ext.android.viewModel


class PriceActivity : BaseActivity<ActivityPriceBinding, PriceViewModel>() {
    override val layoutResourceId = R.layout.activity_price
    override val viewModel: PriceViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, QuestionActivity::class.java))
        })
    }

    override fun initViewFinal() {
        binding.rangePrice.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onRangeChangeListener(
                rangeBar: RangeBar,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String,
                rightPinValue: String
            ) {
            }

            override fun onTouchEnded(rangeBar: RangeBar) {
                Log.e(
                    TAG,
                    "left pin index is ${rangeBar.leftPinValue} and right is ${rangeBar.rightPinValue}"
                )
                binding.tvPrice.text = "${rangeBar.leftPinValue}~${rangeBar.rightPinValue}만원"
                viewModel.btnActivated.value = true
            }

            override fun onTouchStarted(rangeBar: RangeBar) {
            }
        })

    }

}