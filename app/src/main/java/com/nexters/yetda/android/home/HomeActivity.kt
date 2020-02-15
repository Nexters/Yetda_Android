package com.nexters.yetda.android.home

import android.content.Intent
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityHomeBinding
import com.nexters.yetda.android.name.NameActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId = R.layout.activity_home
    override val viewModel: HomeViewModel by viewModel()

    private val TAG = javaClass.simpleName

    override fun initViewStart() {
//        var vm = ViewModelProviders.of(this)[NameViewModel::class.java]
//        binding.vm = vm

        viewModel.getUpdatesInfo()
        viewModel.getPresentsList()
        viewModel.getQuestionsList()
    }

    override fun initDataBinding() {
        binding.setVariable(BR.vm, viewModel)

        /**
         * Kook
         * - Question 개발을 위해 임의로 수정
         */
//        startActivity(Intent(applicationContext, QuestionActivity::class.java))

//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, NameActivity::class.java))
//        })

        viewModel.getAllHistory().observe(this, Observer {
            it?.let {
                val adapter = HomeAdapter(it)
                binding.recyclerView.adapter = adapter
//                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun initViewFinal() {
        imageHomeStart.setOnClickListener {
            startActivity(Intent(this, NameActivity::class.java))
        }
    }

}