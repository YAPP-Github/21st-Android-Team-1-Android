package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.buddycon.domain.model.CouponInfo
import com.yapp.buddycon.domain.repository.GIFTCON_PAGING_SORT
import com.yapp.buddycon.domain.usecase.giftcon.GetGiftconInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GiftConViewModel @Inject constructor(
    private val getGiftconInfoUseCase: GetGiftconInfoUseCase
) : ViewModel() {
    private val sortState = MutableStateFlow(GIFTCON_PAGING_SORT.EXPIREDATE)
    private val _usableState = MutableStateFlow(true)
    val usableState: StateFlow<Boolean> = _usableState.asStateFlow()

    val giftconPagingData = usableState
        .combine(sortState) { usable, sort ->
            usable to sort
        }.distinctUntilChanged()
        .flatMapLatest { requestGiftconList() }
        .cachedIn(viewModelScope)

    fun changeUsable(usable: Boolean) {
        _usableState.value = usable
    }

    private fun requestGiftconList(): Flow<PagingData<CouponInfo>> =
        getGiftconInfoUseCase(usableState.value, sortState.value)
}