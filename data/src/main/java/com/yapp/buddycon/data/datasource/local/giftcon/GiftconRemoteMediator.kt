package com.yapp.buddycon.data.datasource.local.giftcon

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yapp.buddycon.data.db.BuddyConDataBase
import com.yapp.buddycon.data.db.entity.GiftconEntity
import com.yapp.buddycon.data.db.entity.GiftconRemoteKeysEntity
import com.yapp.buddycon.data.network.api.GiftconService
import com.yapp.buddycon.domain.repository.GIFTCON_PAGING_SORT
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val GIFTCON_STARTING_PAEG_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class GiftconRemoteMediator(
    private val usable: Boolean,
    private val sort: GIFTCON_PAGING_SORT,
    private val service: GiftconService,
    private val buddyConDataBase: BuddyConDataBase
) : RemoteMediator<Int, GiftconEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiftconEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyInCurrentItem(state)
                remoteKey?.nextKey?.minus(1) ?: GIFTCON_STARTING_PAEG_INDEX
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyInLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyInFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }
        }

        try {
            val giftcons = service.requestGiftConList(usable, page, state.config.pageSize, sort.value)
            buddyConDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    buddyConDataBase.giftconDao().clearGiftcon()
                    buddyConDataBase.giftconRemoteKeysDao().clearGiftconRemoteKeys()
                }

                val prevKey = if (page == GIFTCON_STARTING_PAEG_INDEX) null else page - 1
                val nextKey = if (giftcons.isEmpty()) null else page + 1
                val keys = giftcons.map {
                    GiftconRemoteKeysEntity(it.id, prevKey, nextKey)
                }
                buddyConDataBase.giftconDao().insertAll(giftcons.map {
                    GiftconEntity(
                        expireDate = it.expireDate,
                        id = it.id,
                        imageUrl = it.imageUrl,
                        name = it.name,
                        usable = usable
                    )
                })
                buddyConDataBase.giftconRemoteKeysDao().insertAl(keys)
            }
            return MediatorResult.Success(giftcons.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyInLastItem(state: PagingState<Int, GiftconEntity>): GiftconRemoteKeysEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { giftconEntity ->
                buddyConDataBase.giftconRemoteKeysDao().getGiftconRemoteKey(giftconEntity.id)
            }
    }

    private suspend fun getRemoteKeyInFirstItem(state: PagingState<Int, GiftconEntity>): GiftconRemoteKeysEntity? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { giftconEntity ->
                buddyConDataBase.giftconRemoteKeysDao().getGiftconRemoteKey(giftconEntity.id)
            }
    }

    private suspend fun getRemoteKeyInCurrentItem(state: PagingState<Int, GiftconEntity>): GiftconRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                buddyConDataBase.giftconRemoteKeysDao().getGiftconRemoteKey(id)
            }
        }
    }
}