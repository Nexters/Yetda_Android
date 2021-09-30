package com.nexters.yetda.android.ui.birthday

import android.content.Intent
import android.view.KeyEvent
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityBirthdayBinding
import com.nexters.yetda.android.ui.price.PriceActivity
import com.nexters.yetda.android.util.ControlKeyboard
import org.koin.android.viewmodel.ext.android.viewModel


class BirthdayActivity : BaseActivity<ActivityBirthdayBinding>() {
    override val layoutResourceId = R.layout.activity_birthday
    val viewModel: BirthdayViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
        binding.vm = viewModel
        viewModel.getUserFromIntent(intent)
    }

    override fun initDataBinding() {

        viewModel.startNextActivityEvent.observe(this, Observer {
            val intent = Intent(applicationContext, PriceActivity::class.java)
            intent.putExtra("NAME", viewModel.name.value)
            intent.putExtra("GENDER", viewModel.gender)
            intent.putExtra("BIRTHDAY", viewModel.birthday)
            startActivity(intent)
            ControlKeyboard.hide(this)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
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


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DEL -> {
                if (binding.edBirthdayM2.text.isEmpty()) {
                    binding.edBirthdayM1.setText("")
                    binding.edBirthdayM1.requestFocus()
                    return true
                }
                if (binding.edBirthdayD1.text.isEmpty()) {
                    binding.edBirthdayM2.setText("")
                    binding.edBirthdayM2.requestFocus()
                    return true
                }
                if (binding.edBirthdayD2.text.isEmpty()) {
                    binding.edBirthdayD1.setText("")
                    binding.edBirthdayD1.requestFocus()
                    return true
                }
                return false
            }
        }
        return true
    }

}