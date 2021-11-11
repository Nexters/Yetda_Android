package com.nexters.yetda.android.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentHomeBinding
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.ui.question.QuestionCancelDialog
import com.nexters.yetda.android.util.BackPressCloseHandler
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(),HistoryLisner {
    override val layoutResourceId = R.layout.fragment_home
    val viewModel: HomeViewModel by viewModel()

    private val list: ArrayList<History> by lazy { arrayListOf<History>() }
    private val TAG = javaClass.simpleName
    private var backPressCloseHandler: BackPressCloseHandler? = null

    lateinit var dialog: QuestionCancelDialog
    lateinit var prefs: SharedPreferences

    private var flagEast = false
    private var flagEgg = false

   private val adapter by lazy{ HomeAdapter(list, this)}

    override fun initViewStart() {

        viewModel.getUpdatesInfo()
        viewModel.initAskedStatus()
        prefs = requireActivity().getSharedPreferences("Pref", MODE_PRIVATE)

        backPressCloseHandler = BackPressCloseHandler(requireActivity())
    }

    override fun initDataBinding() {
        binding.vm = viewModel

        viewModel.startNextActivityEvent.observe(this, Observer {
            findNavController().navigate(HomeFragmentDirections.actionHomeToName())
        })


        binding.recyclerView.adapter = adapter

        viewModel.getAllHistory().observe(this, Observer {
            it?.let {

                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                viewModel.isEmptyList.value = list.size == 0
            }
        })
    }

    override fun initViewFinal() {
        binding.textHomeTitle.setOnClickListener {
            flagEast = true
            showMemberActivity()
        }

        binding.imageHomePresent.setOnClickListener {
            flagEgg = true
            showMemberActivity()
        }

        binding.textHomeGiftListTitle.setOnClickListener {
//            startActivity(Intent(context, SampleActivity::class.java))
        }

        checkFirstRun()
    }

    fun showMemberActivity() {
        if (flagEast && flagEgg) {
            flagEast = false
            flagEgg = false
            findNavController().navigate(HomeFragmentDirections.actionHomeToMember())
        }
    }

    //todo: backpressed 처리
//    override fun onBackPressed() {
//        backPressCloseHandler!!.onBackPressed()
//    }

    override fun onDelClick(item:History) {
        adapter.deleteHistory(item)
        viewModel.deleteById(item.id);
    }

    fun checkFirstRun() {
        val isFirstRun: Boolean = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            dialog = QuestionCancelDialog.getInstance(getString(R.string.app_remove_guide), true) {
                // 작성할 필요 없음
            }
            dialog.show(requireActivity().supportFragmentManager, "QuestionCancelDialog")
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }
    }
}