package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.GiftConDetail
import com.yapp.buddycon.domain.usecase.giftcon.GetCouponDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GiftConDetailViewModel @Inject constructor(
    private val getCouponDetailUseCase: GetCouponDetailUseCase
) : ViewModel() {

    private val _couponDetailState = MutableSharedFlow<GiftConDetail>(replay = 1)
    val couponDetailState = _couponDetailState.asSharedFlow()

    fun getGiftconDetailInfo(giftId: Int) {
        getCouponDetailUseCase(giftId)
            .catch { e -> Timber.e("GiftconDetailViewModel error ${e.localizedMessage}") }
            .onEach { _couponDetailState.emit(it) }
            .launchIn(viewModelScope)
    }
}