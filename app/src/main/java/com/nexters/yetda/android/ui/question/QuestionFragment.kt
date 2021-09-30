package com.nexters.yetda.android.ui.question


import android.view.View
import androidx.fragment.app.Fragment
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseFragment
import com.nexters.yetda.android.databinding.FragmentQuestionBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : BaseFragment<FragmentQuestionBinding>() {

    private val TAG = javaClass.simpleName

    companion object {
        fun newInstance(): QuestionFragment {
            return QuestionFragment()
        }
    }

    val viewModel: QuestionViewModel by viewModel()
    override val layoutResourceId: Int = R.layout.fragment_question

    override fun initViewStart() {
        binding.vm = viewModel
//        viewModel.addPerson()
    }

    override fun initDataBinding() {
//        viewModel.startNextActivityEvent.observe(this, Observer {
//            if (it is String) {
//                Log.d(TAG, "* * * text : $it")
//            }
//            Log.d(TAG, "* * * QuestionFragment text_idontknow")
//        })
    }

    override fun initViewFinal(view: View) {
        //
    }
}
