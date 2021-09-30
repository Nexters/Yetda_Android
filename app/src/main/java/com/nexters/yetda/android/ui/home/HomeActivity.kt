package com.nexters.yetda.android.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.Observer
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.databinding.ActivityHomeBinding
import com.nexters.yetda.android.ui.member.MemberActivity
import com.nexters.yetda.android.ui.name.NameActivity
import com.nexters.yetda.android.ui.question.QuestionCancelDialog
import com.nexters.yetda.android.util.BackPressCloseHandler
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val layoutResourceId = R.layout.activity_home
    val viewModel: HomeViewModel by viewModel()

    private val list: ArrayList<History> by lazy { arrayListOf<History>() }
    private val TAG = javaClass.simpleName
    private var backPressCloseHandler: BackPressCloseHandler? = null

    lateinit var dialog: QuestionCancelDialog
    lateinit var prefs: SharedPreferences

    private var flagEast = false
    private var flagEgg = false


    override fun initViewStart() {

        viewModel.getUpdatesInfo()
        viewModel.initAskedStatus()
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        backPressCloseHandler = BackPressCloseHandler(this)
    }

    override fun initDataBinding() {
        binding.vm = viewModel

        viewModel.startNextActivityEvent.observe(this, Observer {
            startActivity(Intent(applicationContext, NameActivity::class.java))
            Log.e(TAG, "click ${list}")
        })

        val adapter = HomeAdapter(list)
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
        textHomeTitle.setOnClickListener {
            flagEast = true
            showMemberActivity()
        }

        imageHomePresent.setOnClickListener {
            flagEgg = true
            showMemberActivity()
        }

        checkFirstRun()
    }

    fun showMemberActivity() {
        if (flagEast && flagEgg) {
            flagEast = false
            flagEgg = false
            val intent = Intent(this, MemberActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        backPressCloseHandler!!.onBackPressed()
    }

    fun checkFirstRun() {
        val isFirstRun: Boolean = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            dialog = QuestionCancelDialog.getInstance(getString(R.string.app_remove_guide), true) {
                // 작성할 필요 없음
            }
            dialog.show(supportFragmentManager, "QuestionCancelDialog")
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }
    }
}