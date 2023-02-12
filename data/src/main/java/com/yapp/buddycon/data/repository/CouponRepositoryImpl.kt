package com.yapp.buddycon.data.repository

import androidx.paging.*
import com.yapp.buddycon.data.datasource.local.coupon.CouponRemoteMediator
import com.yapp.buddycon.data.db.BuddyConDataBase
import com.yapp.buddycon.data.network.api.CouponService
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponService: CouponService,
    private val buddyConDataBase: BuddyConDataBase
) : CouponRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCouponList(
        usable: Boolean,
        sort: SortMode,
        couponType: CouponType
    ): Flow<PagingData<CouponItem>> = Pager(
        config = PagingConfig(pageSize = COUPON_PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = CouponRemoteMediator(
            usable,
            sort,
            couponType,
            couponService,
            buddyConDataBase
        ),
        pagingSourceFactory = {
            when (sort) {
                SortMode.NoShared -> {
                    buddyConDataBase.couponDao().getCouponByShared()
                }
                SortMode.ExpireDate -> {
                    buddyConDataBase.couponDao().getCouponByExpireDate(usable)
                }
                SortMode.Name -> {
                    buddyConDataBase.couponDao().getCouponByName(usable)
                }
                SortMode.CreatedAt -> {
                    buddyConDataBase.couponDao().getCouponByCreatedAt(usable)
                }
            }
        }
    ).flow.map { data ->
        data.map { it.toModel() }
    }

    companion object {
        const val COUPON_PAGE_SIZE = 10
    }
}