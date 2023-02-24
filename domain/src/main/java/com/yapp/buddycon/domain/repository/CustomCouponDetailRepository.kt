package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.CouponInputInfo
import kotlinx.coroutines.flow.Flow

interface CustomCouponDetailRepository {
    fun requestCustomDetail(id: Int): Flow<CouponInputInfo>
}