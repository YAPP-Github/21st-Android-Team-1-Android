package com.yapp.buddycon.data.datasource.remote.coupon

import com.yapp.buddycon.data.network.api.CustomCouponDetailService
import com.yapp.buddycon.data.network.response.CustomCouponInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCustomCouponDetailRemoteDataSourceImpl @Inject constructor(
    private val customCouponDetailService: CustomCouponDetailService
) : GetCustomCouponDetailRemoteDataSource {
    override fun requestCustomDetail(id: Int): Flow<CustomCouponInfoResponse> = flow {
        emit(customCouponDetailService.requestCustomDetail(id))
    }
}