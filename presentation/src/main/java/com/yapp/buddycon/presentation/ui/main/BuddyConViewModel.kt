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

    private val _isDimState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDimState: StateFlow<Boolean> = _isDimState.asStateFlow()

    private val _isFabState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFabState: StateFlow<Boolean> = _isFabState.asStateFlow()

    private val _isTooltipState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTooltipState: StateFlow<Boolean> = _isTooltipState.asStateFlow()

    private val _filterState: MutableStateFlow<Int> = MutableStateFlow(0)
    val filterState: StateFlow<Int> = _filterState.asStateFlow()

    private val _isBottomSheetState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBottomSheetState: StateFlow<Boolean>  = _isBottomSheetState.asStateFlow()

    init {
        viewModelScope.launch {
            _isTooltipState.value = getBootInfoUseCase().first().not()
        }
    }

    fun saveBootInfo() {
        viewModelScope.launch {
            saveBootInfoUseCase()
        }
    }

    fun changeFab() {
        _isFabState.value = !_isFabState.value
        _isDimState.value = _isDimState.value.not()
        _isTooltipState.value = false
    }

    fun closeTooltip() {
        _isTooltipState.value = false
    }

    fun changeFilterList(filter: Int) {
        _filterState.value = filter
        _isBottomSheetState.value = false
        _isDimState.value = false
    }

    fun changeBottomSheet() {
        _isBottomSheetState.value = _isBottomSheetState.value.not()
        _isDimState.value = _isDimState.value.not()
    }

    fun hideBottomSheet(){
        _isBottomSheetState.value = false
        _isDimState.value =false
    }
}