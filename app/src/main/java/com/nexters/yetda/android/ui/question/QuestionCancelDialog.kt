package com.nexters.yetda.android.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nexters.yetda.android.R
import com.nexters.yetda.android.databinding.DialogQuestionCancelBinding

class QuestionCancelDialog : DialogFragment() {

    lateinit var callback: (Boolean) -> Unit
    lateinit var message: String
    var oneButtonStatus = false

    companion object {
        fun getInstance(
            msg: String,
            oneButtonStatus: Boolean,
            callback: (Boolean) -> Unit
        ): QuestionCancelDialog {
            val dialog = QuestionCancelDialog()
            dialog.message = msg
            dialog.oneButtonStatus = oneButtonStatus
            dialog.callback = callback
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogQuestionCancelBinding.inflate(
            LayoutInflater.from(container?.context), container, false
        )

        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_r10)
        binding.textQuestionCancelMessage.text = message

        if (oneButtonStatus) {
            binding.textQuestionCancelCancel.visibility = View.GONE
        }

        binding.textQuestionCancelProceed.setOnClickListener {
            callback.invoke(true)
            dismissAllowingStateLoss()
        }
        binding.textQuestionCancelCancel.setOnClickListener {
            callback.invoke(false)
            dismissAllowingStateLoss()
        }

        return binding.root
    }
}