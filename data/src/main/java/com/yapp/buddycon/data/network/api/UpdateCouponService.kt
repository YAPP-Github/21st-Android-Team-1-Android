package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.response.UpdateCouponResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UpdateCouponService {
    @PUT("api/v1/coupon/giftcon/{id}")
    suspend fun updateCoupon(
        @Path("id") id: Int,
        @Body updateCouponRequest: UpdateCouponRequest
    ): UpdateCouponResponse
}