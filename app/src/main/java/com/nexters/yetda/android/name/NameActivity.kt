package com.nexters.yetda.android.name

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.birthday.BirthdayActivity
import com.nexters.yetda.android.databinding.ActivityNameBinding
import com.nexters.yetda.android.gender.GenderActivity
import org.koin.android.viewmodel.ext.android.viewModel


class NameActivity : BaseKotlinActivity<ActivityNameBinding, NameViewModel>() {
    override val layoutResourceId = R.layout.activity_name
    override val viewModel: NameViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
//        var vm = ViewModelProviders.of(this)[NameViewModel::class.java]
//        binding.vm = vm

        //set title, db open등
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {
        //        Crashlytics.getInstance().crash() // Force a crash

        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, GenderActivity::class.java))
        })
    }

    override fun initViewFinal() {
        viewModel.getFirebaseSampleData()

    }


}