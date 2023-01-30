package com.yapp.buddycon.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.login.GetBootInfoUseCase
import com.yapp.buddycon.domain.usecase.login.SaveBootInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BuddyConViewModel @Inject constructor(
    private val getBootInfoUseCase: GetBootInfoUseCase,
    private val saveBootInfoUseCase: SaveBootInfoUseCase
) : ViewModel() {

    private val _isFabState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFabState: StateFlow<Boolean> = _isFabState.asStateFlow()

    private val _isTooltipState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTooltipState: StateFlow<Boolean> = _isTooltipState.asStateFlow()

    init {
        viewModelScope.launch {
            _isTooltipState.value = getBootInfoUseCase().first().not()
        }
    }

    fun changeFab() {
        _isFabState.value = !_isFabState.value
        _isTooltipState.value = false
    }

    fun closeTooltip(){
        _isTooltipState.value = false
    }

    fun saveBootInfo() {
        viewModelScope.launch {
            saveBootInfoUseCase()
        }
    }
}