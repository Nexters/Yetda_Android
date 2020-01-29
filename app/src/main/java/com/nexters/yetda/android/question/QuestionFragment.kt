package com.nexters.yetda.android.question


import androidx.fragment.app.Fragment
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentQuestionBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : BaseFragment<FragmentQuestionBinding, QuestionViewModel>() {

    companion object {
        fun newInstance(): QuestionFragment {
            return QuestionFragment()
        }
    }

    override val viewModel: QuestionViewModel by viewModel()
    override val layoutResourceId: Int = R.layout.fragment_question

    override fun initViewStart() {
        //
    }

    override fun initDataBinding() {
        //
    }

    override fun initViewFinal() {
        //
    }

}
