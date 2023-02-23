package com.yapp.buddycon.data.datasource.remote.coupon.update

import com.google.gson.Gson
import com.yapp.buddycon.data.network.api.UpdateCouponService
import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.response.UpdateCouponResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UpdateCouponRemoteDataSourceImpl @Inject constructor(
    private val updateCouponService: UpdateCouponService
): UpdateCouponRemoteDataSource {
    override fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int?,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResponse> = flow{
        emit(
            updateCouponService.updateCoupon(
                id,
                UpdateCouponRequest(
                    expireDate, isMoneyCoupon, (leftMoney ?: 0), memo, name, storeName
                )
            )
        )
    }
}