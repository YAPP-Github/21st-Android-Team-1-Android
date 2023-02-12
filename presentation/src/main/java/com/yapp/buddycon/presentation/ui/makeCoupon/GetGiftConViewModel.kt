package com.yapp.buddycon.presentation.ui.makeCoupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.buddycon.domain.model.CouponItem
import com.yapp.buddycon.domain.model.CouponType
import com.yapp.buddycon.domain.model.SortMode
import com.yapp.buddycon.domain.usecase.giftcon.GetCouponInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GetGiftConViewModel @Inject constructor(private val getCouponInfoUseCase: GetCouponInfoUseCase) : ViewModel() {
    val couponPagingData: Flow<PagingData<CouponItem>> = requestCouponList().cachedIn(viewModelScope)

    private fun requestCouponList(): Flow<PagingData<CouponItem>> =
        getCouponInfoUseCase(
            usable = true,
            sort = SortMode.ExpireDate,
            couponType = CouponType.GiftCon
        )
}