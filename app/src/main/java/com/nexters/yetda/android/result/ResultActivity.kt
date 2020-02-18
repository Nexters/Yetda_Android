package com.nexters.yetda.android.result

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityResultBinding
import com.nexters.yetda.android.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel


class ResultActivity : BaseActivity<ActivityResultBinding, ResultViewModel>() {
    override val layoutResourceId = R.layout.activity_result
    override val viewModel: ResultViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)

        //TODO: Sample Code
        val history = intent.getParcelableExtra<History>("ITEM")
        viewModel.name.value = history.name
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        })
    }

    override fun initViewFinal() {
    }

}