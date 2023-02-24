package com.yapp.buddycon.presentation.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.DialogMessageBinding
import com.yapp.buddycon.presentation.databinding.LayoutCouponDialogBinding

// 메세지와 '확인' 버튼 하나만 있는 다이얼로그
class CouponDialogFragment(
    private val message: String,
    private val onConfirmListener: (() -> Unit)? = null
) : BaseDialogFragment<LayoutCouponDialogBinding>(R.layout.layout_coupon_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message = message
    }

    override fun setEvent() {
        super.setEvent()

        binding.tvGoHome.setOnClickListener {
            onConfirmListener?.invoke()
        }
    }
}