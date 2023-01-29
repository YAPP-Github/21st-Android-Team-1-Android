package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.login.GetUserInfoUseCase
import com.yapp.buddycon.domain.usecase.token.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _loginState = MutableSharedFlow<KaKaoLoginState>()
    val loginState: SharedFlow<KaKaoLoginState> = _loginState.asSharedFlow()

    init {
        viewModelScope.launch {
            _loginState.emit(KaKaoLoginState.LogOut)
        }
    }

    fun requestUserInfo(
        kakaoAccessToken: String, name: String, email: String?, gender: String?, ageRange: String?
    ) = getUserInfoUseCase(kakaoAccessToken, name, email, gender, ageRange)
        .catch { e -> _loginState.emit(KaKaoLoginState.Error(e)) }
        .onEach {
            saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn, it.refreshToken)
            _loginState.emit(KaKaoLoginState.Login(it))
        }.launchIn(viewModelScope)
}