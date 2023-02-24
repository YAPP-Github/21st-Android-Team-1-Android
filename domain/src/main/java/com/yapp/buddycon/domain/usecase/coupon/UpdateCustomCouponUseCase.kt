package com.yapp.buddycon.domain.usecase.coupon

import com.yapp.buddycon.domain.model.ChangeCouponResult
import com.yapp.buddycon.domain.repository.ChangeCouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCustomCouponUseCase @Inject constructor(
    private val changeCouponRepository: ChangeCouponRepository
) {
    operator fun invoke(
        id: Int,
        name: String,
        expireDate: String,
        storeName: String,
        sentMemberName: String,
        memo: String
    ): Flow<ChangeCouponResult> =
        changeCouponRepository.updateCustomCoupon(
            id, name, expireDate, storeName, sentMemberName, memo
        )
}