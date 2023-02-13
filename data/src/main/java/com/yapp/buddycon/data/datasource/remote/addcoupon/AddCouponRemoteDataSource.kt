package com.yapp.buddycon.data.datasource.remote.addcoupon

import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AddCouponRemoteDataSource {
    fun getGifticonInfo(barcodeNumber: String): Flow<Response<GifticonInfoResponse>>
    fun getCustomCouponInfo(barcodeNumber: String): Flow<Response<CustomCouponInfoResponse>>
}