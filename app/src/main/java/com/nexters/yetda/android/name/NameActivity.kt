package com.nexters.yetda.android.name

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.birthday.BirthdayActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityNameBinding
import com.nexters.yetda.android.gender.GenderActivity
import com.nexters.yetda.android.util.ControlKeyboard
import org.koin.android.viewmodel.ext.android.viewModel


class NameActivity : BaseActivity<ActivityNameBinding, NameViewModel>() {
    override val layoutResourceId = R.layout.activity_name
    override val viewModel: NameViewModel by viewModel()

    private val TAG = javaClass.simpleName


    override fun initViewStart() {
//        binding.vm = vm

        //set title, db open등
        binding.setVariable(BR.vm, viewModel)
    }

    override fun initDataBinding() {
        //        Crashlytics.getInstance().crash() // Force a crash

        viewModel.startNextActivityEvent.observe(this, Observer {
            val intent = Intent(applicationContext, GenderActivity::class.java)
            intent.putExtra("NAME", viewModel.name.value)
            startActivity(intent)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })
    }

    override fun initViewFinal() {
//        viewModel.getFirebaseSampleData()
//        ControlKeyboard.show(this)


    }


}