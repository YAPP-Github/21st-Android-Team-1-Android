package com.yapp.buddycon.presentation.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.DialogMessageBinding
import com.yapp.buddycon.presentation.databinding.LayoutCouponDialogMessageBinding

class CouponMessageDialogFragment(
    private val title: String,
    private val description: String
) : BaseDialogFragment<LayoutCouponDialogMessageBinding>(R.layout.layout_coupon_dialog_message) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title = title
        binding.description = description
    }

    override fun setEvent() {
        super.setEvent()

        binding.tvConfirm.setOnClickListener {
            dismiss()
        }
    }
}