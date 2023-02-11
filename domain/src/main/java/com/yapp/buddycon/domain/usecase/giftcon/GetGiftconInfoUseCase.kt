package com.yapp.buddycon.domain.usecase.giftcon

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.repository.GIFTCON_PAGING_SORT
import com.yapp.buddycon.domain.repository.GiftconRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGiftconInfoUseCase @Inject constructor(
    private val giftconRepository: GiftconRepository
) {
    operator fun invoke(usable: Boolean, sort: GIFTCON_PAGING_SORT): Flow<PagingData<CouponInfo>> =
        giftconRepository.getGiftconList(usable, sort)
}