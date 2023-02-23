package com.yapp.buddycon.domain.usecase.coupon.update

import com.yapp.buddycon.domain.model.UpdateCouponResult
import com.yapp.buddycon.domain.repository.UpdateCouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCouponUseCase @Inject constructor(
    private val updateCouponRepository: UpdateCouponRepository
) {
    operator fun invoke(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResult> =
        updateCouponRepository.updateCoupon(
            id, expireDate, isMoneyCoupon, leftMoney, memo, name, storeName
        )
}