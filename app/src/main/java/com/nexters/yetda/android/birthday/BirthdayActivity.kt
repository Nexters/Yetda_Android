package com.nexters.yetda.android.birthday

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.databinding.ActivityBirthdayBinding
import com.nexters.yetda.android.price.PriceActivity
import org.koin.android.viewmodel.ext.android.viewModel


class BirthdayActivity : BaseKotlinActivity<ActivityBirthdayBinding, BirthdayViewModel>() {
    override val layoutResourceId = R.layout.activity_birthday
    override val viewModel: BirthdayViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext,PriceActivity::class.java))
        })
    }

    override fun initViewFinal() {
    }

}