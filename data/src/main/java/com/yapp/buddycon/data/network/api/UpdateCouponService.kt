package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.response.UpdateCouponResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.PUT
import retrofit2.http.Path

interface UpdateCouponService {
    @PUT("api/v1/coupon/gifticon/{id}")
    suspend fun updateCoupon(
        @Path("id") id: Int,
        @Body updateCouponRequest: UpdateCouponRequest
    ): UpdateCouponResponse
}