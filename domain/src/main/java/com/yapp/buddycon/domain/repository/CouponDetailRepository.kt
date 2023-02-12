package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.GiftConDetail
import kotlinx.coroutines.flow.Flow

interface CouponDetailRepository {
    fun getGiftConDetailCoupon(id : Int) : Flow<GiftConDetail>
}