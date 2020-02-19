package com.nexters.yetda.android.question

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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
    lateinit var tags: ArrayList<String>

    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)

        tags = intent.getStringArrayListExtra("TAGS")
        Log.d(TAG, "* * * tags ::: ${tags[0]}")
        Log.d(TAG, "* * * tags ::: ${tags[1]}")
        viewModel.findQuestion(tags)

        val bundle = Bundle()
//        val bundle = intent.extras
//        addQuestionFragment(bundle)
    }

    override fun initDataBinding() {
//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, ResultActivity::class.java))
//        })

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })

        viewModel.getQustionEvent.observe(this, Observer {
            textQuestionCard.text = it.question
        })

    }

    override fun initViewFinal() {
        imageNoButton.setOnClickListener {
            cardQuestion.animate()
                .translationX(convertDpToPixel(-400f))
                .setDuration(700)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // No를 클릭한 뒤
                }.start()
        }

        imageOkButton.setOnClickListener {
            cardQuestion.animate()
                .translationX(convertDpToPixel(400f))
                .setDuration(700)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // OK를 클릭한 뒤
                }.start()
        }
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