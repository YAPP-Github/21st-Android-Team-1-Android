package com.yapp.buddycon.presentation.ui.addCoupon

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.DialogTwoOptionTypeBinding

// 버튼 2개 존재하는 다이얼로그
class TwoOptionTypeDialogFragment(
    private val message: String,
    private val option1Text: String,
    private val option2Text: String,
    private val onClickOption1Listener: (() -> Unit)? = null,
    private val onClickOption2Listener: (() -> Unit)? = null
) : BaseDialogFragment<DialogTwoOptionTypeBinding>(R.layout.dialog_two_option_type) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            message = this@TwoOptionTypeDialogFragment.message
            option1text = this@TwoOptionTypeDialogFragment.option1Text
            option2text = this@TwoOptionTypeDialogFragment.option2Text
        }
    }

    override fun setEvent() {
        super.setEvent()

        binding.tvOption1.setOnClickListener {
            onClickOption1Listener?.invoke()
            dismiss()
        }

        binding.tvOption2.setOnClickListener {
            onClickOption2Listener?.invoke()
            dismiss()
        }
    }
}