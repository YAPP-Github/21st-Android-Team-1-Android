package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<KaKaoLoginState>(KaKaoLoginState.LogOut)
    val loginState: StateFlow<KaKaoLoginState> = _loginState.asStateFlow()

    fun requestUserInfo(kakaoAccessToken: String) {
        viewModelScope.launch {
            getUserInfoUseCase(kakaoAccessToken)
                .collect { result ->
                    if (result.isSuccess) {
                        _loginState.value = KaKaoLoginState.Login(result.getOrNull())
                    } else {
                        _loginState.value = KaKaoLoginState.Error(result.exceptionOrNull())
                    }
                }
        }
    }
}