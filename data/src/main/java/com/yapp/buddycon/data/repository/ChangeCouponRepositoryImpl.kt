package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.coupon.ChangeCouponRemoteDataSource
import com.yapp.buddycon.domain.model.ChangeCouponResult
import com.yapp.buddycon.domain.repository.ChangeCouponRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeCouponRepositoryImpl @Inject constructor(
    private val changeCouponRemoteDataSource: ChangeCouponRemoteDataSource
) : ChangeCouponRepository {
    override fun updateCoupon(
        id: Int,
        expireDate: String,
        isMoneyCoupon: Boolean,
        leftMoney: Int,
        memo: String,
        name: String,
        storeName: String
    ): Flow<ChangeCouponResult> =
        changeCouponRemoteDataSource.updateCoupon(
            id,
            expireDate,
            isMoneyCoupon,
            leftMoney,
            memo,
            name,
            storeName
        ).map { it.toModel() }

    override fun updateCustomCoupon(
        id: Int,
        name: String,
        expireDate: String,
        storeName: String,
        sentMemberName: String,
        memo: String
    ): Flow<ChangeCouponResult> =
        changeCouponRemoteDataSource.updateCustomCoupon(
            id, name, expireDate, storeName, sentMemberName, memo
        ).map { it.toModel() }

    override fun changeCoupon(id: Int, state: String): Flow<ChangeCouponResult> =
        changeCouponRemoteDataSource.changeCoupon(id, state).map { it.toModel() }

    override fun deleteCoupon(id: Int): Flow<ChangeCouponResult> =
        changeCouponRemoteDataSource.deleteCoupon(id).map { it.toModel() }
}