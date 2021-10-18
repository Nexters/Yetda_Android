package com.nexters.yetda.android.ui.price

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appyvet.materialrangebar.RangeBar
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityPriceBinding
import com.nexters.yetda.android.domain.database.model.History
import org.koin.android.viewmodel.ext.android.viewModel


class PriceActivity : BaseFragment<ActivityPriceBinding>() {
    override val layoutResourceId = R.layout.activity_price
    val viewModel: PriceViewModel by viewModel()
    val args: PriceActivityArgs by navArgs()

    private val TAG = javaClass.simpleName
    var history = History()
    var leftValue = 0
    var rightValue = 0


    override fun initViewStart() {
        binding.vm = viewModel
        history = viewModel.getUserFromIntent(args)
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            history.startPrice = (leftValue * 10000).toLong()
            history.endPrice = (rightValue * 10000).toLong()
            findNavController().navigate(
                PriceActivityDirections.actionPriceToQuestion(
                    viewModel.getTags().toTypedArray(), history
                )
            )

        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            findNavController().popBackStack()
        })
    }

    override fun initViewFinal() {
        binding.rangePrice.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onRangeChangeListener(
                rangeBar: RangeBar,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String,
                rightPinValue: String
            ) {
            }

            override fun onTouchEnded(rangeBar: RangeBar) {
                Log.e(
                    TAG,
                    "left pin index is ${rangeBar.leftIndex} and right is ${rangeBar.rightIndex}"
                )
                leftValue = rangeBar.leftIndex
                rightValue = rangeBar.rightIndex
                binding.tvPrice.text = "${rangeBar.leftPinValue}~${rangeBar.rightPinValue}만원"
                viewModel.btnActivated.value = true
            }

            override fun onTouchStarted(rangeBar: RangeBar) {
                //
            }
        })

//        textPriceNext.setOnClickListener {
////            startActivity(Intent(this, QuestionActivity::class.java))
//        }
    }

}