package com.nexters.yetda.android.question

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.animation.AccelerateInterpolator
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import kotlinx.android.synthetic.main.activity_question.*
import org.koin.android.viewmodel.ext.android.viewModel

class QuestionActivity : BaseActivity<ActivityQuestionBinding, QuestionViewModel>() {
    override val layoutResourceId = R.layout.activity_question
    override val viewModel: QuestionViewModel by viewModel()
    private val TAG = javaClass.simpleName

    private val fragment = QuestionFragment.newInstance()
    private val fragmentTransaction = supportFragmentManager.beginTransaction()

    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)

        val bundle = Bundle()
        bundle.putString("KEY_TEST", "test test 123")
//        val bundle = intent.extras
//        addQuestionFragment(bundle)

        imageNoButton.setOnClickListener {
            cardQuestion.animate()
                .translationX(convertDpToPixel(-400f))
                .setDuration(1000)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
//                    cardQuestion.translationX = 0f
                }.start()
        }

        imageOkButton.setOnClickListener {
            cardQuestion.animate()
                .translationX(convertDpToPixel(400f))
                .setDuration(1000)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
//                    cardQuestion.translationX = 0f
                }.start()

        }
    }

    override fun initDataBinding() {
//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, ResultActivity::class.java))
//        })

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })

    }

    override fun initViewFinal() {
        //
    }

    fun addQuestionFragment(bundle: Bundle) {
        fragment.arguments = bundle
//        fragmentTransaction.add(R.id.layout_question_container, fragment)
        fragmentTransaction.commit()
    }

    private fun transXAni() {
//        val transAnimation: ObjectAnimator = ObjectAnimator.ofFloat(image, "y", image.getY(), 20)
//        transAnimation.duration = duration.toLong()
//        transAnimation.interpolator = AccelerateInterpolator()
//        transAnimation.start()
    }

    fun convertDpToPixel(dp: Float): Float {
        return dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}