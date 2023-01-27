package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.UserInfo
import com.yapp.buddycon.domain.usecase.GetTokenUseCase
import com.yapp.buddycon.domain.usecase.GetUserInfoUseCase
import com.yapp.buddycon.domain.usecase.SaveInitInfoUseCase
import com.yapp.buddycon.domain.usecase.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun requestUserInfo(kakaoAccessToken: String) {
        getUserInfoUseCase(kakaoAccessToken)
            .catch { e -> _loginState.emit(KaKaoLoginState.Error(e)) }
            .onEach {
                saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn)
                _loginState.emit(KaKaoLoginState.Login(it))
            }
            .launchIn(viewModelScope)
    }
}