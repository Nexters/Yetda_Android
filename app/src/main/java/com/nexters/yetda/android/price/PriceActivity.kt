package com.nexters.yetda.android.price

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.databinding.ActivityPriceBinding
import com.nexters.yetda.android.question.QuestionActivity
import org.koin.android.viewmodel.ext.android.viewModel


class PriceActivity : BaseKotlinActivity<ActivityPriceBinding, PriceViewModel>() {
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
    }

}