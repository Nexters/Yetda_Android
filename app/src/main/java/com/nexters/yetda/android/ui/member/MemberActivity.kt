package com.nexters.yetda.android.ui.member

import android.widget.Toast
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityNameBinding
import kotlinx.android.synthetic.main.activity_member.*
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
        // www.naver.com 웹뷰 + 터치 이벤트(클릭 이벤트)

        imageMemberYena.setOnClickListener {
            Toast.makeText(
                this,
                "예나 이미지를 터치 했다",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
