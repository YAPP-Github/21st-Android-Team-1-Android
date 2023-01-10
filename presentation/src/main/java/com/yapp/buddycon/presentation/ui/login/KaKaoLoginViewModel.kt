package com.yapp.buddycon.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<KaKaoLoginState>(KaKaoLoginState.LogOut)
    val loginState: StateFlow<KaKaoLoginState> = _loginState.asStateFlow()

    fun requestUserInfo(kakaoAccessToken: String) {
        getUserInfoUseCase(kakaoAccessToken)
            .catch { e -> _loginState.value = KaKaoLoginState.Error(e) }
            .onEach { _loginState.value = KaKaoLoginState.Login(it) }
            .launchIn(viewModelScope)
    }
}