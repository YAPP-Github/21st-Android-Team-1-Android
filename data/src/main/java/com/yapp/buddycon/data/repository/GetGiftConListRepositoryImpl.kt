package com.yapp.buddycon.data.repository

import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.repository.GetGiftConListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGiftConListRepositoryImpl @Inject constructor() : GetGiftConListRepository {
    override fun getGiftConList(page: Int, size: Int, sort: List<String>): Flow<List<CouponInfo>> {
        TODO("Not yet implemented")
    }
}