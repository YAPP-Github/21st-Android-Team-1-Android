package com.yapp.buddycon.data.datasource.remote.coupon

import com.yapp.buddycon.data.network.api.ChangeCouponService
import com.yapp.buddycon.data.network.request.ChangeCouponRequest
import com.yapp.buddycon.data.network.request.UpdateCouponRequest
import com.yapp.buddycon.data.network.request.UpdateCustomCouponRequest
import com.yapp.buddycon.data.network.response.ChangeCouponResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangeCouponRemoteDataSourceImpl @Inject constructor(
    private val changeCouponService: ChangeCouponService
) : ChangeCouponRemoteDataSource {
    override fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<ChangeCouponResponse> = flow {
        emit(
            changeCouponService.updateCoupon(
                id = id,
                updateCouponRequest = UpdateCouponRequest(
                    expireDate,
                    isMoneyCoupon,
                    leftMoney,
                    memo,
                    name,
                    storeName
                )
            )
        )
    }

    override fun updateCustomCoupon(
        id: Int,
        name: String,
        expireDate: String,
        storeName: String,
        sentMemberName: String,
        memo: String
    ): Flow<ChangeCouponResponse> = flow {
        emit(
            changeCouponService.updateCustomCoupon(
                id = id,
                updateCustomCouponRequest = UpdateCustomCouponRequest(
                    name, expireDate, storeName, sentMemberName, memo
                )
            )
        )
    }

    override fun changeCoupon(id: Int, state: String): Flow<ChangeCouponResponse> = flow {
        emit(
            changeCouponService.changeCoupon(
                id = id,
                changeCouponRequest = ChangeCouponRequest(state)
            )
        )
    }

    override fun deleteCoupon(id: Int): Flow<ChangeCouponResponse> = flow {
        emit(changeCouponService.deleteCoupon(id))
    }
}