package com.nexters.yetda.android.home

import android.content.Intent
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityHomeBinding
import com.nexters.yetda.android.question.QuestionActivity
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId = R.layout.activity_home
    override val viewModel: HomeViewModel by viewModel()

    private val TAG = javaClass.simpleName

    override fun initViewStart() {
//        var vm = ViewModelProviders.of(this)[NameViewModel::class.java]
//        binding.vm = vm
    }

    override fun initDataBinding() {
        binding.setVariable(BR.vm, viewModel)

        /**
         * Kook
         * - Question 개발을 위해 임의로 수정
         */
        startActivity(Intent(applicationContext, QuestionActivity::class.java))

//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, NameActivity::class.java))
//        })
    }
    override fun initViewFinal() {
    }

}