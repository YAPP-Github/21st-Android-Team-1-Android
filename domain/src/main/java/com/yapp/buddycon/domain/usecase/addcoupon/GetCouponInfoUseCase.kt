package com.yapp.buddycon.domain.usecase.addcoupon

import com.yapp.buddycon.domain.repository.AddCouponRepository
import javax.inject.Inject

class GetCouponInfoUseCase @Inject constructor(
    private val addCouponRepository: AddCouponRepository
) {
}