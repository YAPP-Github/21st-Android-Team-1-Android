package com.yapp.buddycon.data.datasource.remote.coupon.update

import com.yapp.buddycon.data.network.api.UpdateCouponService
import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.response.UpdateCouponResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateCouponRemoteDataSourceImpl @Inject constructor(
    private val updateCouponService: UpdateCouponService
): UpdateCouponRemoteDataSource {
    override fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResponse> = flow{
        emit(
            updateCouponService.updateCoupon(
                id,
                UpdateCouponRequest(
                    expireDate = expireDate,
                    isMoneyCoupon = isMoneyCoupon,
                    leftMoney = leftMoney,
                    memo = memo,
                    name = name,
                    storeName = storeName
                )
            )
        )
    }
}