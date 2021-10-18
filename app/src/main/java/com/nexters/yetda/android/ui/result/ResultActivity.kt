package com.nexters.yetda.android.ui.result

import android.content.Intent
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nexters.yetda.android.R
import com.nexters.yetda.android.YetdaApplication
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityResultBinding
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.ui.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ResultActivity : BaseFragment<ActivityResultBinding>() {
    override val layoutResourceId = R.layout.activity_result
    val viewModel: ResultViewModel by viewModel()

    private val TAG = javaClass.simpleName
    var history: History = History()
    var index: Int = 0

    override fun initViewStart() {
        binding.vm = viewModel
        Glide.with(this).load(R.raw.pung).into(binding.ivResultPung)
        //TODO: Sample Code
//        val history = intent.getParcelableExtra<History>("ITEM")
//        viewModel.name.value = history.name
        var id = requireActivity().intent.getIntExtra("hitoryId", 0)
        if (id == 0) {
            //오류
            startActivity(Intent(context, HomeActivity::class.java))
            requireActivity().finish()
        } else {
            history = viewModel.findHistoryById(id)
        }
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(context, HomeActivity::class.java))
            requireActivity().finish()
        })
        setPresent()
        binding.btnResultRepeat.setOnClickListener {
            YetdaApplication.get().progressON(requireActivity())
            setPresent()
        }

    }

    override fun initViewFinal() {
    }

    fun setPresent() {
        if (index < history.presents.size) {
            binding.tvResultPresent.text = history.presents[index]?.name
            Glide
                .with(this)
                .load(history.presents[index]?.image)
                .fitCenter()
                .placeholder(R.drawable.img_present_default)
                .into(binding.ivResultPresent)

            index++
        } else {
            index = 0
        }
        YetdaApplication.get().progressOFF()
    }

    //todo: backpress 처리 필요
//    override fun onBackPressed() {
//        startActivity(Intent(applicationContext, HomeActivity::class.java))
//        finish()
//    }
}