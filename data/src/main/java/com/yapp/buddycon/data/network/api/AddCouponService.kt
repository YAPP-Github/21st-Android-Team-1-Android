package com.yapp.buddycon.data.network.api

import com.yapp.buddycon.data.network.request.CustomCouponAddRequest
import com.yapp.buddycon.data.network.request.GifticonAddRequest
import com.yapp.buddycon.data.network.response.AddCouponResponse
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AddCouponService {
    // 바코드 넘버로 기프티콘 정보 조회하기
    @GET("api/v1/coupon/gifticon/info")
    suspend fun getGifticonInfo(
        @Query("barcode") barcodeNumber: String
    ): Response<GifticonInfoResponse>

    // 바코드 넘버로 제작티콘 정보 불러오기
    @GET("api/v1/coupon/custom-coupon/info")
    suspend fun getCustomCouponInfo(
        @Query("barcode") barcodeNumber: String
    ): Response<CustomCouponInfoResponse>

    // 기프티콘 등록
    @Multipart
    @POST("api/v1/coupon/gifticon")
    suspend fun addGifticon(
        @Part image: MultipartBody.Part,
        @Part gifticonCreationRequestDto: MultipartBody.Part
    ): Response<AddCouponResponse>

    // 제작티콘 등록
    @Multipart
    @POST("api/v1/coupon/custom-coupon")
    suspend fun addCustomCoupon(
        @Part image: MultipartBody.Part,
        @Part customCouponCreationRequestDto: MultipartBody.Part
    ): Response<AddCouponResponse>
}