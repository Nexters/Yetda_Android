package com.nexters.yetda.android.ui.result

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nexters.yetda.android.R
import com.nexters.yetda.android.YetdaApplication
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentResultBinding
import com.nexters.yetda.android.domain.database.model.History
import org.koin.android.viewmodel.ext.android.viewModel

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    override val layoutResourceId = R.layout.fragment_result
    val viewModel: ResultViewModel by viewModel()
    val args: ResultFragmentArgs by navArgs()

    private lateinit var callback: OnBackPressedCallback
    private val TAG = javaClass.simpleName
    var history: History = History()
    var index: Int = 0

    override fun initViewStart() {
        binding.vm = viewModel
        Glide.with(this).load(R.raw.pung).into(binding.ivResultPung)

        var id = args.historyId
        if (id == 0) {
            // id가 0일 수 없으므로 오류 처리용
            findNavController().navigate(ResultFragmentDirections.actionResultToHome())
        } else {
            history = viewModel.findHistoryById(id)
        }
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(ResultFragmentDirections.actionResultToHome())
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ResultFragmentDirections.actionResultToHome())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}