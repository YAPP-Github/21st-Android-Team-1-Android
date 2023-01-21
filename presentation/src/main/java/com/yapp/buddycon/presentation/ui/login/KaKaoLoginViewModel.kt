package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.usecase.GetTokenUseCase
import com.yapp.buddycon.domain.usecase.GetUserInfoUseCase
import com.yapp.buddycon.domain.usecase.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<KaKaoLoginState>(KaKaoLoginState.LogOut)
    val loginState: StateFlow<KaKaoLoginState> = _loginState.asStateFlow()

    val tokenInfoFlow: Flow<Pair<String, Long>> = getTokenUseCase()

    fun requestUserInfo(kakaoAccessToken: String) {
        getUserInfoUseCase(kakaoAccessToken)
            .catch { e -> _loginState.value = KaKaoLoginState.Error(e) }
            .onEach {
                saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn)
                _loginState.value = KaKaoLoginState.Login(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestLogin(accessToken: String, accessTokenExpiresIn: Long){
        _loginState.value = KaKaoLoginState.Login(
            UserInfo(
                accessToken = accessToken,
                accessTokenExpiresIn = accessTokenExpiresIn
            )
        )
    }
}