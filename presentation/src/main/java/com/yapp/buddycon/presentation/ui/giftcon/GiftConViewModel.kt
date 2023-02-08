package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.buddycon.domain.model.GiftconInfo
import com.yapp.buddycon.domain.usecase.giftcon.GetGiftconInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftConViewModel @Inject constructor(
    private val getGiftconInfoUseCase: GetGiftconInfoUseCase
) : ViewModel() {
    private val _tabState = MutableStateFlow(0) // 0 : usable, 1: used
    val tabState = _tabState.asStateFlow()

    val giftconFlow: Flow<PagingData<GiftconInfo>> = getGiftconInfoUseCase().cachedIn(viewModelScope)

    fun changeTab(tab: Int) {
        _tabState.value = tab
    }
}