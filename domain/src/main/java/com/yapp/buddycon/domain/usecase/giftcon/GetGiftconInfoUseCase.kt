package com.yapp.buddycon.domain.usecase.giftcon

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.GiftconInfo
import com.yapp.buddycon.domain.repository.GIFTCON_PAGING_SORT
import com.yapp.buddycon.domain.repository.GiftconRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGiftconInfoUseCase @Inject constructor(
    private val giftconRepository: GiftconRepository
) {
    operator fun invoke(usable: Boolean = true, sort: GIFTCON_PAGING_SORT = GIFTCON_PAGING_SORT.EXPIREDATE): Flow<PagingData<GiftconInfo>> =
        giftconRepository.getGiftconList(usable, sort)
}