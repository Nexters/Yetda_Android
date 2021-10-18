package com.nexters.yetda.android.ui.gender

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityGenderBinding
import com.nexters.yetda.android.ui.birthday.BirthdayActivity
import org.koin.android.viewmodel.ext.android.viewModel


class GenderActivity : BaseFragment<ActivityGenderBinding>() {
    override val layoutResourceId = R.layout.activity_gender
    val viewModel: GenderViewModel by viewModel()
    val args : GenderActivityArgs by navArgs()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.vm = viewModel
        viewModel.setName(args.name)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            val intent = Intent(context, BirthdayActivity::class.java)
            intent.putExtra("NAME", viewModel.name.value)
            intent.putExtra("GENDER", viewModel.getGender())
            startActivity(intent)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            requireActivity().finish()
        })
    }

    override fun initViewFinal() {
    }
}