package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.coupon.delete.DeleteCouponRemoteDataSource
import com.yapp.buddycon.domain.model.DeleteCouponResult
import com.yapp.buddycon.domain.repository.DeleteCouponRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteCouponRepositoryImpl @Inject constructor(
    private val deleteCouponRemoteDataSource: DeleteCouponRemoteDataSource
) : DeleteCouponRepository {
    override fun deleteCoupon(id: Int): Flow<DeleteCouponResult> =
        deleteCouponRemoteDataSource.deleteCoupon(id).map { it.toModel() }
}