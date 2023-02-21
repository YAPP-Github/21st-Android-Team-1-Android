package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.DeleteCouponResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteCouponService {

    @DELETE("api/v1/coupon/{id}")
    suspend fun deleteCoupon(
        @Path("id") id: Int
    ): DeleteCouponResponse
}