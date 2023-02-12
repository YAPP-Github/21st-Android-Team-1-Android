package com.yapp.buddycon.domain.usecase.giftcon

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.domain.repository.CouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCouponInfoUseCase @Inject constructor(
    private val couponRepository: CouponRepository
) {
    operator fun invoke(
        usable: Boolean,
        sort: SortMode,
        couponType: CouponType
    ): Flow<PagingData<CouponItem>> =
        couponRepository.getCouponList(usable, sort, couponType)
}