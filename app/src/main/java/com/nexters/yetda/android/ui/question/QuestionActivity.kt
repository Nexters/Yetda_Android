package com.nexters.yetda.android.ui.question

import android.animation.Animator
import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import com.nexters.yetda.android.domain.database.model.History
import com.nexters.yetda.android.domain.database.model.Question
import org.koin.android.viewmodel.ext.android.viewModel

class QuestionActivity : BaseFragment<ActivityQuestionBinding>() {
    override val layoutResourceId = R.layout.activity_question
    val viewModel: QuestionViewModel by viewModel()
    private val TAG = javaClass.simpleName
    private val args: QuestionActivityArgs by navArgs()

    var question = Question()
    lateinit var tags: ArrayList<String>
    lateinit var history: History
    lateinit var aniAlpha: Animation
    lateinit var dialog: QuestionCancelDialog

    //todo: backpress  처리 필요
//    override fun onBackPressed() {
//        dialog.show(supportFragmentManager, "QuestionCancelDialog")
//    }

    override fun initViewStart() {
        binding.vm = viewModel
        aniAlpha = AnimationUtils.loadAnimation(context, R.anim.ani_fade_in)
        dialog = QuestionCancelDialog.getInstance(getString(R.string.reset_alert), false) {
            if (it) {
                requireActivity().finish()
            }
        }

        tags = args.tags.toCollection(ArrayList())
        history = args.history
        viewModel.saveHistoryInfo(history)
        findQuestionAndPresents()
    }

    override fun initDataBinding() {
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            dialog.show(requireActivity().supportFragmentManager, "QuestionCancelDialog")
        })

        viewModel.getQustionEvent.observe(this, Observer {
            binding.cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
            )
            binding.textQuestionCard.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            question = it
            binding.textQuestionCard.text = it.question
            binding.cardQuestion.visibility = View.VISIBLE
            binding.cardQuestion.startAnimation(aniAlpha)
        })

        viewModel.getQuestionFinished.observe(this, Observer {
            showAnimation()
        })


    }

    override fun initViewFinal() {
        binding.imageNoButton.setOnClickListener {
            binding.cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.genderGrey))
            )
            binding.textQuestionCard.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            binding.cardQuestion.animate()
                .translationX(convertDpToPixel(-400f))
                .setDuration(500)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // No를 클릭한 뒤
                    binding.cardQuestion.visibility = View.GONE
                    binding.cardQuestion.translationX = 0f
                    tags.add(question.tag.trim())
                    findQuestionAndPresents()
                }.start()
        }

        binding.imageOkButton.setOnClickListener {
            binding.cardQuestion.setCardBackgroundColor(
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red))
            )
            binding.textQuestionCard.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            binding.cardQuestion.animate()
                .translationX(convertDpToPixel(400f))
                .setDuration(500)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // OK를 클릭한 뒤
                    binding.cardQuestion.visibility = View.GONE
                    binding.cardQuestion.translationX = 0f
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
                findNavController().navigate(
                    QuestionActivityDirections.actionQuestionToResult(
                        viewModel.historyId
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    "(ㄒoㄒ) 알 수 없는 문제가 생겼습니다.\n앱을 종료 후 다시 시도해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}