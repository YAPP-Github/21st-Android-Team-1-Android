package com.yapp.buddycon.data.datasource.remote

import com.yapp.buddycon.domain.model.CouponInfo
import kotlinx.coroutines.flow.Flow

interface GetGiftConListDataSource {
        fun getGiftConList(
                page : Int,
                size : Int,
                sort : List<String>
        ) : Flow<List<CouponInfo>>
}