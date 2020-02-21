package com.nexters.yetda.android.home

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.YetdaApplication
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityHomeBinding
import com.nexters.yetda.android.name.NameActivity
import com.nexters.yetda.android.question.QuestionActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId = R.layout.activity_home
    override val viewModel: HomeViewModel by viewModel()
    private val list: ArrayList<History> by lazy { arrayListOf<History>() }

    private val TAG = javaClass.simpleName

    override fun initViewStart() {

        viewModel.getUpdatesInfo()
        viewModel.initAskedStatus()

        Log.e(TAG, "initViewStart ${list.toString()}")

        // Hide the status bar.
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun initDataBinding() {
        binding.setVariable(BR.vm, viewModel)

        viewModel.startNextActivityEvent.observe(this, Observer {
                        startActivity(Intent(applicationContext, NameActivity::class.java))
            Log.e(TAG, "click ${list}")
        })

        val adapter = HomeAdapter(list)
        binding.recyclerView.adapter = adapter

        viewModel.getAllHistory().observe(this, Observer {
            it?.let {

                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                viewModel.isEmptyList.value = list.size == 0
            }
        })
    }

    override fun initViewFinal() {
        //
    }

}