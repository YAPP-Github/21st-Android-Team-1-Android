package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import kotlinx.coroutines.flow.Flow

interface CouponRepository {
    fun getCouponList(
        usable: Boolean,
        sort: SortMode,
        couponType: CouponType
    ): Flow<PagingData<CouponItem>>
}
