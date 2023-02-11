package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.GiftconInfo
import kotlinx.coroutines.flow.Flow

enum class SortMode(val value: String){
    NoShared("noshared"),
    Expiredate("expireDate"),
    Name("name"),
    CreatedAt("createdAt")
}

enum class CouponType{
    GiftCon, Custom, Made
}

interface GiftconRepository {
    fun getGiftconList(usable: Boolean, sort: GIFTCON_PAGING_SORT): Flow<PagingData<GiftconInfo>>
}
