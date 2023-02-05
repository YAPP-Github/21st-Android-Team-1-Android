package com.yapp.buddycon.data.datasource.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yapp.buddycon.data.db.BuddyConDataBase
import com.yapp.buddycon.data.db.entity.GiftconEntity
import com.yapp.buddycon.data.network.api.GiftconService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GiftconRemoteMediator @Inject constructor(
    private val usable: Boolean,
    private val sort: String,
    private val service: GiftconService,
    private val buddyConDataBase: BuddyConDataBase
) : RemoteMediator<Int, GiftconEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiftconEntity>
    ): MediatorResult {

    }
}