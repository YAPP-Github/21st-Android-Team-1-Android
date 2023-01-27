package com.yapp.buddycon.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.login.SaveInitInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuddyConViewModel @Inject constructor(
   private val saveInitInfoUseCase: SaveInitInfoUseCase
): ViewModel() {

    private val _isFabState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFabState : StateFlow<Boolean> = _isFabState.asStateFlow()

    fun changeFab() {
        _isFabState.value = !_isFabState.value
    }

    fun saveInitInfo(){
        viewModelScope.launch {
            saveInitInfoUseCase()
        }
    }
}