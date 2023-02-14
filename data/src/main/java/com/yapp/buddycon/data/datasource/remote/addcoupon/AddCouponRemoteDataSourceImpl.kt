package com.yapp.buddycon.data.datasource.remote.addcoupon

import com.yapp.buddycon.data.network.api.AddCouponService
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import com.yapp.buddycon.data.network.response.GifticonInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AddCouponRemoteDataSourceImpl @Inject constructor(
    private val addCouponService: AddCouponService
)  : AddCouponRemoteDataSource {
    override fun getGifticonInfo(barcodeNumber: String): Flow<Response<GifticonInfoResponse>> {
        return flow {
            emit(addCouponService.getGifticonInfo(barcodeNumber))
        }
    }

    override fun getCustomCouponInfo(barcodeNumber: String): Flow<Response<CustomCouponInfoResponse>> {
        return flow {
            emit(addCouponService.getCustomCouponInfo(barcodeNumber))
        }
    }
}