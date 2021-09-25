package com.nexters.yetda.android.ui.member

import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityNameBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MemberActivity : BaseActivity<ActivityNameBinding, MemberViewModel>() {
    override val layoutResourceId = R.layout.activity_member
    override val viewModel: MemberViewModel by viewModel()
    private val TAG = javaClass.simpleName

    override fun initViewStart() {
        //
    }

    override fun initDataBinding() {
        //
    }

    override fun initViewFinal() {
        //
    }
}
