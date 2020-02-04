package com.nexters.yetda.android.gender

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.birthday.BirthdayActivity
import com.nexters.yetda.android.databinding.ActivityGenderBinding
import org.koin.android.viewmodel.ext.android.viewModel


class GenderActivity : BaseKotlinActivity<ActivityGenderBinding, GenderViewModel>() {
    override val layoutResourceId = R.layout.activity_gender
    override val viewModel: GenderViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext,BirthdayActivity::class.java))
        })
    }

    override fun initViewFinal() {
    }
}