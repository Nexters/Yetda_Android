package com.nexters.yetda.android.ui.gender

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.ui.birthday.BirthdayActivity
import com.nexters.yetda.android.databinding.ActivityGenderBinding
import org.koin.android.viewmodel.ext.android.viewModel


class GenderActivity : BaseActivity<ActivityGenderBinding, GenderViewModel>() {
    override val layoutResourceId = R.layout.activity_gender
    override val viewModel: GenderViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.vm = viewModel
        viewModel.getUserFromIntent(intent)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            val intent = Intent(applicationContext, BirthdayActivity::class.java)
            intent.putExtra("NAME", viewModel.name.value)
            intent.putExtra("GENDER", viewModel.getGender())
            startActivity(intent)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })
    }

    override fun initViewFinal() {
    }
}