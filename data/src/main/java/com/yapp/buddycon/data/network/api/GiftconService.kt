package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.GiftconResponse
import retrofit2.http.GET


interface GiftconService {
    @GET("api/v1/coupon/gifticon")
    suspend fun requestGiftConList(
        usable: Boolean,
        page: Int,
        size: Int,
        sort: String
    ): List<GiftconResponse>
}