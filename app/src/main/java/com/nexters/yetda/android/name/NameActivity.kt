package com.nexters.yetda.android.name

import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseKotlinActivity
import com.nexters.yetda.android.databinding.ActivityNameBinding
import org.koin.android.viewmodel.ext.android.viewModel


class NameActivity : BaseKotlinActivity<ActivityNameBinding, NameViewModel>() {
    override val layoutResourceId = R.layout.activity_name
    override val viewModel: NameViewModel by viewModel()

    private val TAG = "NameActivity"


    override fun initViewStart() {
//        var vm = ViewModelProviders.of(this)[NameViewModel::class.java]
//        binding.vm = vm
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {
        //        Crashlytics.getInstance().crash() // Force a crash

    }

    override fun initViewFinal() {
        viewModel.getFirebaseSampleData()
    }

}