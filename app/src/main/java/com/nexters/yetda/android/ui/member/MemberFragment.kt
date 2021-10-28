package com.nexters.yetda.android.ui.member

import android.content.Intent
import android.net.Uri
import android.view.View
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentMemberBinding

class MemberFragment : BaseFragment<FragmentMemberBinding>() {
    override val layoutResourceId = R.layout.fragment_member
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
        setSnsListener(binding.imageMemberJisu, "https://www.facebook.com/jisookim1993")
        setSnsListener(binding.imageMemberKook, "https://instagram.com/bingocake")
        setSnsListener(binding.imageMemberHeeju, "https://instagram.com/meiyoubella00/")
        setSnsListener(binding.imageMemberGong, "https://instagram.com/xeesoxee/?hl=ko")
        setSnsListener(binding.imageMemberDohyun, "https://www.naver.com")
        setSnsListener(binding.imageMemberSuhyun, "https://instagram.com/dev_suhyun/")
        setSnsListener(binding.imageMemberYena, "https://instagram.com/d.nowme/")
    }
}
