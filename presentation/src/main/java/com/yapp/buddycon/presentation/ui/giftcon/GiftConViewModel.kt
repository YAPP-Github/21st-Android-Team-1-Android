package com.yapp.buddycon.presentation.ui.giftcon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GiftConViewModel @Inject constructor(

) : ViewModel() {
    private val _tabState = MutableStateFlow(0) // 0 : usable, 1: used
    val tabState = _tabState.asStateFlow()

    fun changeTab(tab: Int) {
        _tabState.value = tab
    }
}