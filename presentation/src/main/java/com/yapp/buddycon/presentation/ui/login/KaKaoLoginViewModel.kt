package com.yapp.buddycon.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.UserInfo
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
            //saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn, it.refreshToken)
            saveTokenUseCase(
                "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjc0ODEyMjk5LCJleHAiOjE2ODA4MTcwOTl9.8buxTCLp_erwERq7d96AORKCyzbLNaqhg7ozNFKs0_M",
                Long.MAX_VALUE, ""
            )
            _loginState.emit(KaKaoLoginState.Login(it))
        }.launchIn(viewModelScope)

    fun loginGuest(){
        viewModelScope.launch {
            saveTokenUseCase(
                "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MTAsImlhdCI6MTY3NjgyNTIxMywiZXhwIjoxNjgyODczMjEzfQ._BOIpITxcnRKNY3VNUKwSOha92ybSHMLnkbnBGr5EV4",
                1682873213340,
                "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzc0MzAwMTN9.fRdkH8YnbtWRC7JWqpgYjHERM32dKo0dJEiqmPCcQ54"
            )
            _loginState.emit(KaKaoLoginState.Login(
                UserInfo(
                    "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MTAsImlhdCI6MTY3NjgyNTIxMywiZXhwIjoxNjgyODczMjEzfQ._BOIpITxcnRKNY3VNUKwSOha92ybSHMLnkbnBGr5EV4",
                    "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzc0MzAwMTN9.fRdkH8YnbtWRC7JWqpgYjHERM32dKo0dJEiqmPCcQ54",
                    1682873213340
                )
            ))
        }
    }
}