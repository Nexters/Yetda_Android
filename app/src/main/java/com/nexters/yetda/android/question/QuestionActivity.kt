package com.nexters.yetda.android.question

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.Question
import com.nexters.yetda.android.databinding.ActivityQuestionBinding
import kotlinx.android.synthetic.main.activity_question.*
import org.koin.android.viewmodel.ext.android.viewModel

class QuestionActivity : BaseActivity<ActivityQuestionBinding, QuestionViewModel>() {
    override val layoutResourceId = R.layout.activity_question
    override val viewModel: QuestionViewModel by viewModel()

    private val TAG = javaClass.simpleName
    private val fragment = QuestionFragment.newInstance()
    private val fragmentTransaction = supportFragmentManager.beginTransaction()

    var question = Question()
    lateinit var tags: ArrayList<String>
    lateinit var aniAlpha: Animation

    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
        aniAlpha = AnimationUtils.loadAnimation(this, R.anim.ani_fade_in)
//        aniAlpha.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(p0: Animation?) {
//                //
//            }
//
//            override fun onAnimationEnd(p0: Animation?) {
//                //
//            }
//
//            override fun onAnimationStart(p0: Animation?) {
//                //
//            }
//        })

        tags = intent.getStringArrayListExtra("TAGS")
        Log.d(TAG, "* * * tags ::: ${tags[0]}")
        Log.d(TAG, "* * * tags ::: ${tags[1]}")
        viewModel.findQuestion(tags)
    }

    override fun initDataBinding() {
//        viewModel.startNextActivityEvent.observe(this, Observer {
//            startActivity(Intent(applicationContext, ResultActivity::class.java))
//        })

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })

        viewModel.getQustionEvent.observe(this, Observer {
            question = it
            textQuestionCard.text = it.question
            cardQuestion.visibility = View.VISIBLE
            cardQuestion.startAnimation(aniAlpha)
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
                    cardQuestion.visibility = View.GONE
                    cardQuestion.translationX = 0f
                    tags.add(question.tag)
                    viewModel.findQuestion(tags)
                }.start()
        }

        imageOkButton.setOnClickListener {
            cardQuestion.animate()
                .translationX(convertDpToPixel(400f))
                .setDuration(700)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    // OK를 클릭한 뒤
                    cardQuestion.visibility = View.GONE
                    cardQuestion.translationX = 0f
                    viewModel.findQuestion(tags)
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

    private fun convertDpToPixel(dp: Float): Float {
        return dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}