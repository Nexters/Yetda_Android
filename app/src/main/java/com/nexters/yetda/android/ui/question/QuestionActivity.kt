package com.nexters.yetda.android.ui.question

import android.animation.Animator
import android.content.Intent
import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.domain.database.model.Question
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import com.nexters.yetda.android.ui.result.ResultActivity
import kotlinx.android.synthetic.main.activity_question.*
import org.koin.android.viewmodel.ext.android.viewModel

class QuestionActivity : BaseActivity<ActivityQuestionBinding>() {
    override val layoutResourceId = R.layout.activity_question
    val viewModel: QuestionViewModel by viewModel()
    private val TAG = javaClass.simpleName

    var question = Question()
    lateinit var tags: ArrayList<String>
    lateinit var history: History
    lateinit var aniAlpha: Animation
    lateinit var dialog: QuestionCancelDialog

    override fun onBackPressed() {
        dialog.show(supportFragmentManager, "QuestionCancelDialog")
    }

    override fun initViewStart() {
        binding.vm = viewModel
        aniAlpha = AnimationUtils.loadAnimation(this, R.anim.ani_fade_in)
        dialog = QuestionCancelDialog.getInstance(getString(R.string.reset_alert), false) {
            if (it) {
                finish()
            }
        }

        tags = intent.getStringArrayListExtra("TAGS")
        history = intent.getParcelableExtra<History>("ITEM")
        viewModel.saveHistoryInfo(history)
        findQuestionAndPresents()
    }

    override fun initDataBinding() {
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            dialog.show(supportFragmentManager, "QuestionCancelDialog")
        })

        viewModel.getQustionEvent.observe(this, Observer {
            cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            )
            textQuestionCard.setTextColor(ContextCompat.getColor(this, R.color.black))
            question = it
            textQuestionCard.text = it.question
            cardQuestion.visibility = View.VISIBLE
            cardQuestion.startAnimation(aniAlpha)
        })

        viewModel.getQuestionFinished.observe(this, Observer {
            showAnimation()
        })


    }

    override fun initViewFinal() {
        imageNoButton.setOnClickListener {
            cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.genderGrey))
            )
            textQuestionCard.setTextColor(ContextCompat.getColor(this, R.color.white))

            cardQuestion.animate()
                .translationX(convertDpToPixel(-400f))
                .setDuration(500)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // No를 클릭한 뒤
                    cardQuestion.visibility = View.GONE
                    cardQuestion.translationX = 0f
                    tags.add(question.tag.trim())
                    findQuestionAndPresents()
                }.start()
        }

        imageOkButton.setOnClickListener {
            cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            )
            textQuestionCard.setTextColor(ContextCompat.getColor(this, R.color.white))

            cardQuestion.animate()
                .translationX(convertDpToPixel(400f))
                .setDuration(500)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // OK를 클릭한 뒤
                    cardQuestion.visibility = View.GONE
                    cardQuestion.translationX = 0f
                    findQuestionAndPresents()
                }.start()
        }
    }

    fun findQuestionAndPresents() {

        val presentList = viewModel.findPresents(tags, history.startPrice, history.endPrice)

        if (presentList.size > 10) {
            viewModel.findQuestion(tags)
        } else {
            // 선물이 10개 이하인 경우 결과 화면 출력
//            viewModel.showResult()
            //초기에 데이터를 못갖고 오기도 함.
            if (presentList.size != 0)
                showAnimation()
        }
    }

    private fun convertDpToPixel(dp: Float): Float {
        return dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.initAskedStatus()
    }

    fun showAnimation() {
        viewModel.showResult()
        binding.viewQuestionResult.visibility = View.VISIBLE
        binding.lottieQuestionResult.visibility = View.VISIBLE
        binding.lottieQuestionResult.playAnimation()
        binding.lottieQuestionResult.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.lottieQuestionResult.visibility = View.GONE
                binding.lottieQuestionResultRepeat.visibility = View.VISIBLE
                binding.lottieQuestionResultRepeat.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        binding.lottieQuestionResultRepeat.setOnClickListener {
            viewModel.addHistory()

            if (viewModel.historyId > 0) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("hitoryId", viewModel.historyId)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "(ㄒoㄒ) 알 수 없는 문제가 생겼습니다.\n앱을 종료 후 다시 시도해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}