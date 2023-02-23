package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.ChangeCouponResult
import kotlinx.coroutines.flow.Flow

interface ChangeCouponRepository {
    fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<ChangeCouponResult>

    fun changeCoupon(
        id: Int,
        state: String
    ): Flow<ChangeCouponResult>

    fun deleteCoupon(
        id: Int
    ): Flow<ChangeCouponResult>
}