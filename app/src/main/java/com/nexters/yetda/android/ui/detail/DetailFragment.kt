package com.nexters.yetda.android.ui.detail

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentDetailBinding
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.domain.database.model.Present
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.*

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override val layoutResourceId = R.layout.fragment_detail
    val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    private val TAG = javaClass.simpleName
    private var toast: Toast? = null
    var history: History = History()

    override fun initViewStart() {

        history = args.history

    }

    override fun initDataBinding() {
        binding.vm = viewModel
        val presents: ArrayList<Present> = ArrayList<Present>()
        presents.addAll(history.presents)

        val adapter = DetailAdapter(presents)
        binding.cardDetail.adapter = adapter

        binding.tvDetailName.text = history.name + resources.getString(R.string.detail)

        binding.tvDelete.setOnClickListener {
            viewModel.clickDeleteButton(history.id)
        }


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (history.birthday.length == 4) {
                    val newFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.ENGLISH)
                    val date = MonthDay.of(
                        history.birthday.substring(0, 2).toInt(),
                        history.birthday.substring(2, 4).toInt()
                    )
                    binding.tvDetailBirthday.text = date.format(newFormatter)
                } else {
                    binding.tvDetailBirthday.text = history.birthday
                }
            } else {
                if (history.birthday.length == 4) {
                    binding.tvDetailBirthday.text =
                        "${history.birthday.substring(0, 2)}월 ${history.birthday.substring(2, 4)}일"
                } else {
                    binding.tvDetailBirthday.text = history.birthday
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "* * * e ::: ${e.message}")
            if (history.birthday.length == 4) {
                binding.tvDetailBirthday.text =
                    "${history.birthday.substring(0, 2)}월 ${history.birthday.substring(2, 4)}일"
            } else {
                binding.tvDetailBirthday.text = history.birthday
            }
        }


//        binding.tvDetailBirthday.text = history.birthday
        binding.tvDetailPrice.text =
            "${NumberFormat.getCurrencyInstance(Locale.KOREA).format(history.startPrice)} ~ ${
                NumberFormat.getCurrencyInstance(
                    Locale.KOREA
                ).format(history.endPrice)
            }"

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            findNavController().popBackStack()
        })

        viewModel.toastBeforeActivityEvent.observe(this, Observer {
            toast = Toast.makeText(
                activity, "삭제 버튼을 한번 더 누르시면 삭제됩니다.",
                Toast.LENGTH_SHORT
            )
            toast!!.show()
        })

        viewModel.deleteBeforeActivityEvent.observe(this, Observer {
            findNavController().popBackStack()
        })
    }

    override fun initViewFinal() {
    }

}