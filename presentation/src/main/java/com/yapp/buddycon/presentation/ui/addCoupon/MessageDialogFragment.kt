package com.yapp.buddycon.presentation.ui.addCoupon

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.DialogMessageBinding

// 메세지와 '확인' 버튼 하나만 있는 다이얼로그
class MessageDialogFragment(
    private val message: String,
    private val onConfirmListener: (() -> Unit)? = null
) : BaseDialogFragment<DialogMessageBinding>(R.layout.dialog_message) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message = message
    }

    override fun setEvent() {
        super.setEvent()

        binding.tvConfirm.setOnClickListener {
            onConfirmListener?.invoke()
            dismiss()
        }
    }
}