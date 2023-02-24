package com.yapp.buddycon.presentation.ui.common.dialog

import com.bumptech.glide.Glide
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.LayoutCouponImageDialogMessageBinding

class CouponImageDialogFragment(
    private val imageUrl: String
) : BaseDialogFragment<LayoutCouponImageDialogMessageBinding>(R.layout.layout_coupon_image_dialog_message) {
    override fun setEvent() {
        super.setEvent()
        Glide.with(binding.ivDetailCoupon.context)
            .load(imageUrl)
            .into(binding.ivDetailCoupon)

        binding.ivClose.setOnClickListener { dismiss() }
    }
}