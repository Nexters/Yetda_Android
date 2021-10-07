package com.nexters.yetda.android.ui.name

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityNameBinding
import com.nexters.yetda.android.ui.gender.GenderActivity
import org.koin.android.viewmodel.ext.android.viewModel


class NameActivity : BaseFragment<ActivityNameBinding>() {
    override val layoutResourceId = R.layout.activity_name
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
            val intent = Intent(requireContext(), GenderActivity::class.java)
            intent.putExtra("NAME", viewModel.name.value)
            startActivity(intent)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            requireActivity().finish()
        })
    }

    override fun initViewFinal(view: View) {


    }


}