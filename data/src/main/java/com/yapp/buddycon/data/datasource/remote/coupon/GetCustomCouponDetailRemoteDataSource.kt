package com.yapp.buddycon.data.datasource.remote.coupon

import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import kotlinx.coroutines.flow.Flow

interface GetCustomCouponDetailRemoteDataSource {
    fun requestCustomDetail(id: Int): Flow<CustomCouponInfoResponse>
}