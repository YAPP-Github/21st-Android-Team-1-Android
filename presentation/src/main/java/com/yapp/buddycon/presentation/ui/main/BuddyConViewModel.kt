package com.yapp.buddycon.presentation.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BuddyConViewModel : ViewModel() {

    private val _isFabState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFabState : StateFlow<Boolean> = _isFabState.asStateFlow()

    fun changeFab() {
        _isFabState.value = !_isFabState.value
    }
}