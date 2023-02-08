package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.GiftconInfo
import kotlinx.coroutines.flow.Flow

enum class GIFTCON_PAGING_SORT(val value: String) {
    EXPIREDATE("expireDate"), NAME("name")
    //CREATEDAT("createdAt")
}

interface GiftconRepository {
    fun getGiftconList(usable: Boolean, sort: GIFTCON_PAGING_SORT): Flow<PagingData<GiftconInfo>>
}
