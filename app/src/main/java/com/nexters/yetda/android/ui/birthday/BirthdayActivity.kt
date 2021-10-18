package com.nexters.yetda.android.ui.birthday

import android.view.KeyEvent
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityBirthdayBinding
import com.nexters.yetda.android.util.ControlKeyboard
import org.koin.android.viewmodel.ext.android.viewModel


class BirthdayActivity : BaseFragment<ActivityBirthdayBinding>() {
    override val layoutResourceId = R.layout.activity_birthday
    val viewModel: BirthdayViewModel by viewModel()
    val args: BirthdayActivityArgs by navArgs()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.vm = viewModel
        viewModel.getUserFromIntent(args)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(
                BirthdayActivityDirections.actionBirthdayToPrice(
                    viewModel.name.value ?: "",
                    viewModel.gender,
                    viewModel.birthday
                )
            )
            ControlKeyboard.hide(requireActivity())
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            findNavController().popBackStack()
        })

    }

    override fun initViewFinal() {

        binding.edBirthdayM1.doAfterTextChanged {
            if (!binding.edBirthdayM1.text.isEmpty()) {
                binding.edBirthdayM2.requestFocus()
            }
        }
        binding.edBirthdayM2.doAfterTextChanged {
            if (!binding.edBirthdayM2.text.isEmpty()) {
                binding.edBirthdayD1.requestFocus()
            }
        }
        binding.edBirthdayD1.doAfterTextChanged {
            if (!binding.edBirthdayD1.text.isEmpty()) {
                binding.edBirthdayD2.requestFocus()
            }
        }
        binding.edBirthdayD2.doAfterTextChanged {
            if (binding.edBirthdayD2.text.isEmpty()) {
                viewModel.validBirthday(false)
            } else {
                viewModel.validBirthday(true)
            }
        }

        //todo: 동작하는지 확인 필요
        binding.root.setOnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_DEL -> {
                    if (binding.edBirthdayM2.text.isEmpty()) {
                        binding.edBirthdayM1.setText("")
                        binding.edBirthdayM1.requestFocus()
                        true
                    }
                    if (binding.edBirthdayD1.text.isEmpty()) {
                        binding.edBirthdayM2.setText("")
                        binding.edBirthdayM2.requestFocus()
                        true
                    }
                    if (binding.edBirthdayD2.text.isEmpty()) {
                        binding.edBirthdayD1.setText("")
                        binding.edBirthdayD1.requestFocus()
                        true
                    }
                    false
                }
            }
            true
        }

    }

}