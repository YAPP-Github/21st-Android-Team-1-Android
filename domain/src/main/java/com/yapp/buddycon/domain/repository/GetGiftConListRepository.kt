package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.CouponInfo
import kotlinx.coroutines.flow.Flow

interface GetGiftConListRepository {
    fun getGiftConList(page : Int, size : Int, sort : List<String>) : Flow<List<CouponInfo>>
}