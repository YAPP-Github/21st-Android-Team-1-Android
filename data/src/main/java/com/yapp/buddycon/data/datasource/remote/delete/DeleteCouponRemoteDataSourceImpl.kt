package com.yapp.buddycon.data.datasource.remote.delete

import com.yapp.buddycon.data.network.api.DeleteCouponService
import com.yapp.buddycon.data.network.response.DeleteCouponResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCouponRemoteDataSourceImpl @Inject constructor(
    private val deleteCouponService: DeleteCouponService
): DeleteCouponRemoteDataSource {
    override fun deleteCoupon(id: Int): Flow<DeleteCouponResponse> = flow{
        emit(deleteCouponService.deleteCoupon(id))
    }
}