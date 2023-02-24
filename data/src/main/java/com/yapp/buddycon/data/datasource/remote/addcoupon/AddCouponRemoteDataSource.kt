package com.yapp.buddycon.data.datasource.remote.addcoupon

import android.net.Uri
import com.yapp.buddycon.data.network.request.CustomCouponAddRequest
import com.yapp.buddycon.data.network.request.GifticonAddRequest
import com.yapp.buddycon.data.network.response.AddCouponResponse
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AddCouponRemoteDataSource {
    fun getGifticonInfo(barcodeNumber: String): Flow<Response<GifticonInfoResponse>>
    fun getCustomCouponInfo(barcodeNumber: String): Flow<Response<CustomCouponInfoResponse>>
    fun addGifticon(imageUriPath: String, addGifticonAddRequest: GifticonAddRequest): Flow<Response<AddCouponResponse>>
    fun addCustomCoupon(imageUriPath: String, customCouponAddRequest: CustomCouponAddRequest): Flow<Response<AddCouponResponse>>
}