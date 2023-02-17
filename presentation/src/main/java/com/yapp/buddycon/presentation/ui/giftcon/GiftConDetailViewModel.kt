package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.usecase.giftcon.GetCouponDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiftConDetailViewModel @Inject constructor(
    private val getCouponDetailUseCase: GetCouponDetailUseCase
): ViewModel(){

    fun getGiftconDetailInfo(giftId: Int){

    }
}