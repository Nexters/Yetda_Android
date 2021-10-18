package com.nexters.yetda.android.ui.result

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nexters.yetda.android.R
import com.nexters.yetda.android.YetdaApplication
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityResultBinding
import com.nexters.yetda.android.domain.database.model.History
import org.koin.android.viewmodel.ext.android.viewModel

class ResultActivity : BaseFragment<ActivityResultBinding>() {
    override val layoutResourceId = R.layout.activity_result
    val viewModel: ResultViewModel by viewModel()
    val args: ResultActivityArgs by navArgs()

    private val TAG = javaClass.simpleName
    var history: History = History()
    var index: Int = 0

    override fun initViewStart() {
        binding.vm = viewModel
        Glide.with(this).load(R.raw.pung).into(binding.ivResultPung)
        //TODO: Sample Code
//        val history = intent.getParcelableExtra<History>("ITEM")
//        viewModel.name.value = history.name
        var id = args.historyId
        if (id == 0) {
            //오류
            findNavController().navigate(ResultActivityDirections.actionResultToHome())
        } else {
            history = viewModel.findHistoryById(id)
        }
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(ResultActivityDirections.actionResultToHome())
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