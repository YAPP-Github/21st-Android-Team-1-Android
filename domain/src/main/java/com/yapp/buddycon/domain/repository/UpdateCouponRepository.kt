package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.UpdateCouponResult
import kotlinx.coroutines.flow.Flow

interface UpdateCouponRepository {
    fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResult>
}