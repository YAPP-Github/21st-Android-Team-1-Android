package com.yapp.buddycon.domain.usecase.coupon

import com.yapp.buddycon.domain.model.ChangeCouponResult
import com.yapp.buddycon.domain.repository.ChangeCouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCouponUseCase @Inject constructor(
    private val changeCouponRepository: ChangeCouponRepository
) {
    operator fun invoke(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<ChangeCouponResult> =
        changeCouponRepository.updateCoupon(
            id, expireDate, isMoneyCoupon, leftMoney, memo, name, storeName
        )
}