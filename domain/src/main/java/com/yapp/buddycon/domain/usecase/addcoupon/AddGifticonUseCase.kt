package com.yapp.buddycon.domain.usecase.addcoupon

import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.repository.AddCouponRepository
import javax.inject.Inject

class AddGifticonUseCase @Inject constructor(private val addCouponRepository: AddCouponRepository) {
    operator fun invoke(imageUriPath: String, couponInputInfo: CouponInputInfo) =
        addCouponRepository.addGifticon(imageUriPath, couponInputInfo)
}