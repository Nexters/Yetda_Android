package com.nexters.yetda.android.ui.member

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityNameBinding
import kotlinx.android.synthetic.main.activity_member.*

class MemberActivity : BaseActivity<ActivityNameBinding>() {
    override val layoutResourceId = R.layout.activity_member
    private val TAG = javaClass.simpleName

    override fun initViewStart() {
        //
    }

    override fun initDataBinding() {
        //
    }

    override fun initViewFinal() {
        // www.naver.com 웹뷰 + 터치 이벤트(클릭 이벤트)

        imageMemberJisu.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/xeesoxee/?hl=ko"))
            startActivity(intent)
        }
        imageMemberYena.setOnClickListener {
            Toast.makeText(
                this,
                "예나 이미지를 터치 했다",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
