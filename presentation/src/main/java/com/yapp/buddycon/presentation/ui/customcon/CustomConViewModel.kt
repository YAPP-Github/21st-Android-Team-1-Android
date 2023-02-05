package com.yapp.buddycon.presentation.ui.customcon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CustomConViewModel @Inject constructor(

): ViewModel(){
    private val _tabState = MutableStateFlow(0) // 0 : usable, 1: used, 2: maed
    val tabState = _tabState.asStateFlow()

    fun changeTab(tab: Int) {
        _tabState.value = tab
    }
}