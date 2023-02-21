package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.DeleteCouponResult
import kotlinx.coroutines.flow.Flow

interface DeleteCouponRepository {
    fun deleteCoupon(id: Int): Flow<DeleteCouponResult>
}