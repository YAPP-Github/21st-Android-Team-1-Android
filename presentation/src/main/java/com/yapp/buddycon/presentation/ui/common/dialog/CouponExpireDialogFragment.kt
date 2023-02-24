package com.yapp.buddycon.presentation.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.LayoutCouponExpireDialogBinding

class CouponExpireDialogFragment(
    private val title: String,
    private val description: String
) : BaseDialogFragment<LayoutCouponExpireDialogBinding>(R.layout.layout_coupon_expire_dialog) {

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