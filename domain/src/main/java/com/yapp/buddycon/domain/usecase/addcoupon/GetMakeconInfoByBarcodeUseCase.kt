package com.yapp.buddycon.domain.usecase.addcoupon

import com.yapp.buddycon.domain.repository.AddCouponRepository
import javax.inject.Inject

class GetMakeconInfoByBarcodeUseCase @Inject constructor(
    private val addCouponRepository: AddCouponRepository
) {
    operator fun invoke(barcodeNumber: String) {

    }
}