package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.coupon.update.UpdateCouponRemoteDataSource
import com.yapp.buddycon.domain.model.UpdateCouponResult
import com.yapp.buddycon.domain.repository.UpdateCouponRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateCouponRepositoryImpl @Inject constructor(
    private val updateCouponRemoteDataSource: UpdateCouponRemoteDataSource
): UpdateCouponRepository {
    override fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<UpdateCouponResult> =
        updateCouponRemoteDataSource.updateCoupon(id, expireDate, isMoneyCoupon, leftMoney, memo, name, storeName)
            .map { it.toModel() }
}