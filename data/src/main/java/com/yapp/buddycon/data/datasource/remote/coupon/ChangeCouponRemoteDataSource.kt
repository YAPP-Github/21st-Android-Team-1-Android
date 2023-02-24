package com.yapp.buddycon.data.datasource.remote.coupon

import com.yapp.buddycon.data.network.response.ChangeCouponResponse
import kotlinx.coroutines.flow.Flow

interface ChangeCouponRemoteDataSource {
    fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<ChangeCouponResponse>

    fun updateCustomCoupon(
        id: Int,
        name: String,
        expireDate: String,
        storeName: String,
        sentMemberName: String,
        memo: String
    ): Flow<ChangeCouponResponse>

    fun changeCoupon(
        id: Int,
        state: String
    ): Flow<ChangeCouponResponse>

    fun deleteCoupon(
        id: Int
    ): Flow<ChangeCouponResponse>
}