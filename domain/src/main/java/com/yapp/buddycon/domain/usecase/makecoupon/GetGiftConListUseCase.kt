package com.yapp.buddycon.domain.usecase.makecoupon

import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.repository.GetGiftConListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGiftConListUseCase @Inject constructor(private val getGiftConListRepository: GetGiftConListRepository) {
    operator fun invoke(page: Int, size: Int, sort: List<String>): Flow<List<CouponInfo>> =
        getGiftConListRepository.getGiftConList(
            page, size, sort
        )
}