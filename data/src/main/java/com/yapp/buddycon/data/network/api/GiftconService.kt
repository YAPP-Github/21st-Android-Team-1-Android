package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.GiftconResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GiftconService {
    @GET("api/v1/coupon/gifticon")
    suspend fun requestGiftConList(
        @Query("usable") usable: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): List<GiftconResponse>
}