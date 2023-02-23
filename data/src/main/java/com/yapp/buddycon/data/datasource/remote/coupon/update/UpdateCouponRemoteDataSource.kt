package com.yapp.buddycon.data.datasource.remote.coupon.update

import com.yapp.buddycon.data.network.response.UpdateCouponResponse
import kotlinx.coroutines.flow.Flow

interface UpdateCouponRemoteDataSource {
    fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int?,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResponse>
}