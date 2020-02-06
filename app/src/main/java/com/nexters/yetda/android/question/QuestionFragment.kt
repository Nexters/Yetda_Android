package com.nexters.yetda.android.question


import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentQuestionBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : BaseFragment<FragmentQuestionBinding, QuestionViewModel>() {

    private val TAG = javaClass.simpleName

    companion object {
        fun newInstance(): QuestionFragment {
            return QuestionFragment()
        }
    }

    override val viewModel: QuestionViewModel by viewModel()
    override val layoutResourceId: Int = R.layout.fragment_question

    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
        viewModel.addPerson()
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            if (it is String) {
                Log.d(TAG, "* * * text : $it")
            }
            Log.d(TAG, "* * * QuestionFragment text_idontknow")
        })
    }

    override fun initViewFinal(view: View) {
        //
    }
}
