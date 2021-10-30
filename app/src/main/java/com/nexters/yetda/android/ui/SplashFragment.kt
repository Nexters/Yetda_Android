package com.nexters.yetda.android.ui

import android.animation.Animator
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_splash

    override fun initViewStart() {
        // Hide the status bar.
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                findNavController().navigate(SplashFragmentDirections.actionSplashToHome())
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    override fun initDataBinding() {
    }

    override fun initViewFinal() {
    }
}
