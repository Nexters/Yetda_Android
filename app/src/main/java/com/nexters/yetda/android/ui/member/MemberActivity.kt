package com.nexters.yetda.android.ui.member

import android.content.Intent
import android.net.Uri
import android.view.View
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

    fun setSnsListener(member: View, snsUri: String) {
        member.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(snsUri))
            startActivity(intent)
        }
    }

    override fun initViewFinal() {
        setSnsListener(imageMemberJisu, "https://www.facebook.com/jisookim1993")
        setSnsListener(imageMemberKook, "https://instagram.com/bingocake")
        setSnsListener(imageMemberHeeju, "https://instagram.com/meiyoubella00/")
        setSnsListener(imageMemberGong, "https://instagram.com/xeesoxee/?hl=ko")
        setSnsListener(imageMemberDohyun, "https://www.naver.com")
        setSnsListener(imageMemberSuhyun, "https://instagram.com/dev_suhyun/")
        setSnsListener(imageMemberYena, "https://instagram.com/d.nowme/")
    }
}
