package com.yapp.buddycon.domain.usecase.coupon

import com.yapp.buddycon.domain.model.ChangeCouponResult
import com.yapp.buddycon.domain.repository.ChangeCouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCouponUseCase @Inject constructor(
    private val changeCouponRepository: ChangeCouponRepository
) {
    operator fun invoke(id: Int): Flow<ChangeCouponResult> =
        changeCouponRepository.deleteCoupon(id)
}