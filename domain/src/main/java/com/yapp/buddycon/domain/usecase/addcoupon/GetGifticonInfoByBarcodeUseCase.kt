package com.yapp.buddycon.domain.usecase.addcoupon

import com.yapp.buddycon.domain.repository.AddCouponRepository
import javax.inject.Inject

class GetGifticonInfoByBarcodeUseCase @Inject constructor(
    private val addCouponRepository: AddCouponRepository
) {
    operator fun invoke(barcodeNumber: String) =
        addCouponRepository.getGifticionInfo(barcodeNumber)
}