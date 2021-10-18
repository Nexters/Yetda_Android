package com.nexters.yetda.android.ui.gender

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityGenderBinding
import org.koin.android.viewmodel.ext.android.viewModel


class GenderActivity : BaseFragment<ActivityGenderBinding>() {
    override val layoutResourceId = R.layout.activity_gender
    val viewModel: GenderViewModel by viewModel()
    val args: GenderActivityArgs by navArgs()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.vm = viewModel
        viewModel.getUserFromIntent(args)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(
                GenderActivityDirections.actionGenderToBirthday(
                    viewModel.name.value ?: "", viewModel.getGender()
                )
            )
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            findNavController().popBackStack()
        })
    }

    override fun initViewFinal() {
    }
}