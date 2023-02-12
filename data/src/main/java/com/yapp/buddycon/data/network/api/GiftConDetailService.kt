package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.GiftConDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GiftConDetailService {
    @GET("api/v1/coupon/gifticon/{id}")
    suspend fun requestGiftConDetail(
        @Path("id") id : Int
    ) : GiftConDetailResponse
}