package com.yapp.buddycon.data.datasource.local.coupon

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yapp.buddycon.data.db.BuddyConDataBase
import com.yapp.buddycon.data.db.entity.CouponEntity
import com.yapp.buddycon.data.db.entity.CouponRemoteKeysEntity
import com.yapp.buddycon.data.network.api.CouponService
import com.yapp.buddycon.domain.repository.CouponType
import com.yapp.buddycon.domain.repository.SortMode
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

const val COUPON_START_PAEGING_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class CouponRemoteMediator(
    private val usable: Boolean,
    private val sort: SortMode,
    private val couponType: CouponType,
    private val service: CouponService,
    private val buddyConDataBase: BuddyConDataBase
) : RemoteMediator<Int, CouponEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CouponEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyInCurrentItem(state)
                remoteKey?.nextKey?.minus(1) ?: COUPON_START_PAEGING_INDEX
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
            val couponList = when (couponType) {
                CouponType.GiftCon -> service.requestGiftConList(
                    usable,
                    page,
                    state.config.pageSize,
                    sort.value
                )
                CouponType.Custom -> service.requestCustomCouponList(
                    usable,
                    page,
                    state.config.pageSize,
                    sort.value
                )
                CouponType.Made -> service.requestMadeCouponList(
                    page,
                    state.config.pageSize,
                    SortMode.CreatedAt.value
                )
            }

            buddyConDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    buddyConDataBase.couponDao().clearCoupon()
                    buddyConDataBase.couponRemoteKeysDao().clearCouponRemoteKeys()
                }

                val prevKey = if (page == COUPON_START_PAEGING_INDEX) null else page - 1
                val nextKey = if (couponList.isEmpty()) null else page + 1
                val keys = couponList.map { CouponRemoteKeysEntity(it.id, prevKey, nextKey) }
                buddyConDataBase.couponDao().insertAll(couponList.map {
                    it.toEntity(usable)
                })
                buddyConDataBase.couponRemoteKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(couponList.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyInLastItem(state: PagingState<Int, CouponEntity>): CouponRemoteKeysEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { couponEntity ->
                buddyConDataBase.couponRemoteKeysDao().getCouponRemoteKey(couponEntity.id)
            }
    }

    private suspend fun getRemoteKeyInFirstItem(state: PagingState<Int, CouponEntity>): CouponRemoteKeysEntity? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { couponEntity ->
                buddyConDataBase.couponRemoteKeysDao().getCouponRemoteKey(couponEntity.id)
            }
    }

    private suspend fun getRemoteKeyInCurrentItem(state: PagingState<Int, CouponEntity>): CouponRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                buddyConDataBase.couponRemoteKeysDao().getCouponRemoteKey(id)
            }
        }
    }
}