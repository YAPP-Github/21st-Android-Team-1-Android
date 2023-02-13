package com.yapp.buddycon.domain.usecase.giftcon

import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.repository.CouponDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCouponDetailUseCase @Inject constructor(private val couponDetailRepository: CouponDetailRepository) {
    operator fun invoke(id : Int) : Flow<GiftConDetail> = couponDetailRepository.getGiftConDetailCoupon(id)
}