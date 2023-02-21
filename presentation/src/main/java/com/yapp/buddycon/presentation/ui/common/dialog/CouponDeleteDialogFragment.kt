package com.yapp.buddycon.presentation.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.LayoutCouponDeleteDialogBinding

class CouponDeleteDialogFragment(
    private val title: String,
    private val description: String,
    private val deleteListener: () -> Unit
) : BaseDialogFragment<LayoutCouponDeleteDialogBinding>(R.layout.layout_coupon_delete_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title = title
        binding.description = description
    }

    override fun setEvent() {
        super.setEvent()
        binding.tvDelete.setOnClickListener { deleteListener() }
        binding.tvCancel.setOnClickListener { dismiss() }
    }
}