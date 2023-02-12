package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.model.CouponItem
import kotlinx.coroutines.flow.Flow

enum class SortMode(val value: String) {
    NoShared("noshared"),
    ExpireDate("expireDate"),
    Name("name"),
    CreatedAt("createdAt")
}

enum class CouponType {
    GiftCon, Custom, Made
}

interface CouponRepository {
    fun getCouponList(
        usable: Boolean,
        sort: SortMode,
        couponType: CouponType
    ): Flow<PagingData<CouponItem>>
}
