package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.makecon.GetGiftConDetailRemoteDataSource
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.repository.CouponDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CouponDetailRepositoryImpl @Inject constructor(
    private val getGiftConDetailRemote: GetGiftConDetailRemoteDataSource
) : CouponDetailRepository {
    override fun getGiftConDetailCoupon(id: Int): Flow<GiftConDetail> =
        getGiftConDetailRemote.requestGiftConDetail(id).map { it.toModel() }
}