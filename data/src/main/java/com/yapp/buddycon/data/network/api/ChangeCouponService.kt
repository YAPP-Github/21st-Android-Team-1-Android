package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.ChangeCouponRequest
import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.response.ChangeCouponResponse
import retrofit2.http.*

interface ChangeCouponService {
    @PUT("api/v1/coupon/gifticon/{id}")
    suspend fun updateCoupon(
        @Path("id") id: Int,
        @Body updateCouponRequest: UpdateCouponRequest
    ): ChangeCouponResponse

    @PATCH("api/v1/coupon/{id}/state")
    suspend fun changeCoupon(
        @Path("id") id: Int,
        @Body changeCouponRequest: ChangeCouponRequest
    ): ChangeCouponResponse

    @DELETE("api/v1/coupon/{id}")
    suspend fun deleteCoupon(
        @Path("id") id: Int
    ): ChangeCouponResponse
}