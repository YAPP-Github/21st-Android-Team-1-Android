package com.yapp.buddycon.presentation.ui.giftcon

import com.bumptech.glide.Glide
import com.yapp.buddycon.presentation.R
import com.yapp.buddycon.presentation.base.BaseDialogFragment
import com.yapp.buddycon.presentation.databinding.LayoutGiftconImageDialogMessageBinding
import timber.log.Timber

class GiftConImageDialogFragment(
    private val imageUrl: String
) : BaseDialogFragment<LayoutGiftconImageDialogMessageBinding>(R.layout.layout_giftcon_image_dialog_message) {
    override fun setEvent() {
        super.setEvent()
        Glide.with(binding.ivDetailCoupon.context)
            .load(imageUrl)
            .into(binding.ivDetailCoupon)

        binding.ivClose.setOnClickListener { dismiss() }
    }
}