package com.yapp.buddycon.data.datasource.remote.makecon

import com.yapp.buddycon.data.network.response.GiftConDetailResponse
import kotlinx.coroutines.flow.Flow

interface GetGiftConDetailRemoteDataSource {
    fun requestGiftConDetail(id : Int) : Flow<GiftConDetailResponse>
}