package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.coupon.GetCustomCouponDetailRemoteDataSource
import com.yapp.buddycon.data.network.api.CustomCouponDetailService
import com.yapp.buddycon.domain.model.CouponInputInfo
import com.yapp.buddycon.domain.repository.CustomCouponDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomCouponDetailRepositoryImpl @Inject constructor(
    private val customCouponDetailRemoteDataSource: GetCustomCouponDetailRemoteDataSource
) : CustomCouponDetailRepository {
    override fun requestCustomDetail(id: Int): Flow<CouponInputInfo> =
        customCouponDetailRemoteDataSource.requestCustomDetail(id).map { it.mapToCouponInputInfo() }
}