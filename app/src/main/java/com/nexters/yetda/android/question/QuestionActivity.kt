package com.nexters.yetda.android.question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import com.nexters.yetda.android.result.ResultActivity
import org.koin.android.viewmodel.ext.android.viewModel


class QuestionActivity : BaseActivity<ActivityQuestionBinding, QuestionViewModel>() {
    override val layoutResourceId = R.layout.activity_question
    override val viewModel: QuestionViewModel by viewModel()

    private val TAG = javaClass.simpleName

    private val fragment = QuestionFragment.newInstance()
    private val fragmentTransaction = supportFragmentManager.beginTransaction()

    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)

        val bundle = Bundle()
        bundle.putString("KEY_TEST", "test test 123")
//        val bundle = intent.extras
        addQuestionFragment(bundle)
    }

    override fun initDataBinding() {
//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, ResultActivity::class.java))
//        })

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })

    }

    override fun initViewFinal() {
        //
    }

    fun addQuestionFragment(bundle: Bundle) {
        fragment.arguments = bundle
        fragmentTransaction.add(R.id.layout_question_container, fragment)
        fragmentTransaction.commit()
    }

}