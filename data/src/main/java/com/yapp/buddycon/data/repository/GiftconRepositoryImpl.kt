package com.yapp.buddycon.data.repository

import androidx.paging.*
import com.yapp.buddycon.data.datasource.local.giftcon.GiftconRemoteMediator
import com.yapp.buddycon.data.db.BuddyConDataBase
import com.yapp.buddycon.data.network.api.CouponService
import com.yapp.buddycon.domain.model.GiftconInfo
import com.yapp.buddycon.domain.repository.GIFTCON_PAGING_SORT
import com.yapp.buddycon.domain.repository.GiftconRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GiftconRepositoryImpl @Inject constructor(
    private val giftconService: CouponService,
    private val buddyConDataBase: BuddyConDataBase
) : GiftconRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGiftconList(
        usable: Boolean,
        sort: GIFTCON_PAGING_SORT
    ): Flow<PagingData<GiftconInfo>> = Pager(
        config = PagingConfig(pageSize = GIFTCON_PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = GiftconRemoteMediator(
            usable,
            sort,
            giftconService,
            buddyConDataBase
        ),
        pagingSourceFactory = {
            when (sort) {
                GIFTCON_PAGING_SORT.EXPIREDATE -> {
                    buddyConDataBase.giftconDao().getGiftconByExpireDate(usable)
                }
                GIFTCON_PAGING_SORT.NAME -> {
                    buddyConDataBase.giftconDao().getGiftconByName(usable)
                }
                // GIFTCON_PAGING_SORT.CREATEDAT -> // TODO
            }
        }
    ).flow.map { data ->
        data.map {
            GiftconInfo(
                expireDate = it.expireDate,
                id = it.id,
                imageUrl = it.imageUrl,
                name = it.name,
                usable = it.usable
            )
        }
    }

    companion object {
        const val GIFTCON_PAGE_SIZE = 10
    }
}