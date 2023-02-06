package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.GiftconInfo
import kotlinx.coroutines.flow.Flow

enum class GIFTCON_PAGING_SORT(val value: String) {
    EXPIREDATE("expireDate"), EXPIREDATE_ASC("expireDate,ASC"),
    NAME("name"), NAME_ASC("name,ASC"),
    CREATEDAT("createdAt"), CREATEDAT_ASC("createdAt,ASC")
}

interface GiftconRepository {
    fun getGiftconList(usable: Boolean, sort: GIFTCON_PAGING_SORT): Flow<PagingData<GiftconInfo>>
}
