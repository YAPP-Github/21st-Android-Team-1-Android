package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomCouponDetailService {
    @GET("api/v1/coupon/custom-coupon/{id}")
    suspend fun requestCustomDetail(
        @Path("id") id: Int
    ): CustomCouponInfoResponse
}