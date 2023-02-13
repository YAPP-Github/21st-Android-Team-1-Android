package com.yapp.buddycon.data.datasource.remote.makecon

import com.yapp.buddycon.data.network.api.GiftConDetailService
import com.yapp.buddycon.data.network.response.GiftConDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGiftConDetailRemoteDataSourceImpl @Inject constructor(private val giftconService: GiftConDetailService) :
    GetGiftConDetailRemoteDataSource {
    override fun requestGiftConDetail(id: Int): Flow<GiftConDetailResponse> = flow {
        emit(giftconService.requestGiftConDetail(id))
    }
}