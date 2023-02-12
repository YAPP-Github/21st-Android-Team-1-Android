package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AddCouponService {
    @GET("api/v1/coupon/gifticon/info") // 바코드 넘버로 기프티콘 정보 조회하기
    suspend fun getGifticonInfo(
        @Query("barcode") barcodeNumber: String
    ): Response<GifticonInfoResponse>

    @GET("api/v1/coupon/custom-coupon/info")
    suspend fun getCustomCouponInfo(
        @Query("barcode") barcodeNumber: String
    ): Response<CustomCouponInfoResponse>
}