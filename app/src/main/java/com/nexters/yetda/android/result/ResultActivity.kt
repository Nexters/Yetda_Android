package com.nexters.yetda.android.result

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityResultBinding
import com.nexters.yetda.android.home.HomeActivity
import kotlinx.android.synthetic.main.item_detail_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class ResultActivity : BaseActivity<ActivityResultBinding, ResultViewModel>() {
    override val layoutResourceId = R.layout.activity_result
    override val viewModel: ResultViewModel by viewModel()

    private val TAG = javaClass.simpleName
    var history: History = History()

    var index: Int = 0
    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
        Glide.with(this).load(R.raw.pung).into(binding.ivResultPung)
        //TODO: Sample Code
//        val history = intent.getParcelableExtra<History>("ITEM")
//        viewModel.name.value = history.name
        var id = intent.getIntExtra("hitoryId", 0)
        if (id == 0) {
            //오류
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        } else {
            history = viewModel.findHistoryById(id)
        }

    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        })
        setPresent()
        binding.btnResultRepeat.setOnClickListener {
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
    }

}