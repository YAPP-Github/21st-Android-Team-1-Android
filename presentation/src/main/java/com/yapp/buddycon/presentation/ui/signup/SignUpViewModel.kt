package com.yapp.buddycon.presentation.ui.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(){
    private val _allTerms = MutableStateFlow(false)
    val allTerms: StateFlow<Boolean> = _allTerms.asStateFlow()

    private val _useTerms = MutableStateFlow(false)
    val useTerms: StateFlow<Boolean> = _useTerms.asStateFlow()

    private val _privacyInfoTerms = MutableStateFlow(false)
    val privacyInfoTerms: StateFlow<Boolean> = _privacyInfoTerms.asStateFlow()

    fun clickAllTerms(){
        val check = _allTerms.value.not()
        _allTerms.value = check
        _useTerms.value = check
        _privacyInfoTerms.value = check
    }

    fun clickUseTerms(){
        _useTerms.value = _useTerms.value.not()
        _allTerms.value = _useTerms.value && _privacyInfoTerms.value
    }

    fun clickPrivacyInfoTerms(){
        _privacyInfoTerms.value = _privacyInfoTerms.value.not()
        _allTerms.value = _useTerms.value && _privacyInfoTerms.value
    }
}