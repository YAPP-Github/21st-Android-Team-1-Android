package com.yapp.buddycon.domain.usecase.coupon

import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.repository.CustomCouponDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomCouponDetailUseCase @Inject constructor(
    private val getCustomCouponDetailRepository: CustomCouponDetailRepository
) {
    operator fun invoke(id: Int): Flow<CouponInputInfo> =
        getCustomCouponDetailRepository.requestCustomDetail(id)
}