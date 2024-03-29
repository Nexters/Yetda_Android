package com.nexters.yetda.android.ui.name

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentNameBinding
import org.koin.android.viewmodel.ext.android.viewModel


class NameFragment : BaseFragment<FragmentNameBinding>() {
    override val layoutResourceId = R.layout.fragment_name
    val viewModel: NameViewModel by viewModel()

    private val TAG = javaClass.simpleName

    override fun initViewStart() {
//        binding.vm = vm

        //set title, db open등
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        //        Crashlytics.getInstance().crash() // Force a crash

        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(
                NameFragmentDirections.actionNameToGender(
                    viewModel.name.value ?: ""
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