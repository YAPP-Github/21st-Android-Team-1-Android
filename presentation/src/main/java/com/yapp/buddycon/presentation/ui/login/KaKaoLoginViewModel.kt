package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.GetUserInfoUseCase
import com.yapp.buddycon.domain.usecase.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _loginState = MutableStateFlow<KaKaoLoginState>(KaKaoLoginState.LogOut)
    val loginState: StateFlow<KaKaoLoginState> = _loginState.asStateFlow()

    fun requestUserInfo(kakaoAccessToken: String) {
        getUserInfoUseCase(kakaoAccessToken)
            .catch { e -> _loginState.value = KaKaoLoginState.Error(e) }
            .onEach {
                saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn)
                _loginState.value = KaKaoLoginState.Login(it)
            }
            .launchIn(viewModelScope)
    }
}