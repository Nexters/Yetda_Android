package com.nexters.yetda.android.question

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import com.nexters.yetda.android.result.ResultActivity
import org.koin.android.viewmodel.ext.android.viewModel


class QuestionActivity : BaseKotlinActivity<ActivityQuestionBinding, QuestionViewModel>() {
    override val layoutResourceId = R.layout.activity_question
    override val viewModel: QuestionViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, ResultActivity::class.java))
        })
    }

    override fun initViewFinal() {
    }

}