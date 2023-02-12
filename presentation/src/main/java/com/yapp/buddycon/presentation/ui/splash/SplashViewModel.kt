package com.yapp.buddycon.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.login.GetBootInfoUseCase
import com.yapp.buddycon.domain.usecase.token.GetTokenUseCase
import com.yapp.buddycon.domain.usecase.token.RefreshTokenUseCase
import com.yapp.buddycon.domain.usecase.token.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getBootInfoUseCase: GetBootInfoUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private var idx = 0
    private val _walkThroughState = MutableStateFlow(
        WalkThroughState(
            idx = idx,
            image = idx.toWalkThroughImage(),
            title = idx.toWalkThroughTitle(),
            subTitle = idx.toWalkThroughSubTitle()
        )
    )
    val walkThroughState = _walkThroughState.asStateFlow()

    private val _splashResultState = MutableStateFlow<SplashResultState>(SplashResultState.Splash)
    val splashResultState = _splashResultState.asStateFlow()

    init {
        getBootInfoUseCase()
            .combine(getTokenUseCase()) { bootInfo, tokenInfo ->
                Timber.d("TokenInfo accessToken: ${tokenInfo.accessToken}, refreshToken: ${tokenInfo.refreshToken} accessTokenExpiration: ${tokenInfo.accessTokenExpiresIn}")
                if (bootInfo) {
                    val currentTime = System.currentTimeMillis()
                    val (accessToken, refreshToken, accessTokenExpiration) = tokenInfo

                    if (accessToken.isEmpty() && refreshToken.isEmpty() && accessTokenExpiration == 0L) _splashResultState.value =
                        SplashResultState.KaKaoLogin
                    else if (accessTokenExpiration < currentTime) requestRefreshToken(
                        accessToken,
                        refreshToken
                    )
                    else _splashResultState.value = SplashResultState.BuddyCon
                } else {
                    _splashResultState.value = SplashResultState.WalkThrough
                }
            }
            .launchIn(viewModelScope)
    }

    fun nextWalkThrough() {
        idx++
        _walkThroughState.value = WalkThroughState(
            idx = idx,
            image = idx.toWalkThroughImage(),
            title = idx.toWalkThroughTitle(),
            subTitle = idx.toWalkThroughSubTitle()
        )
    }

    private fun requestRefreshToken(accessToken: String, refreshToken: String) {
        refreshTokenUseCase(accessToken, refreshToken)
            .catch {
                Timber.e("requestRefreshToken Error: ${it.localizedMessage}")
                _splashResultState.value = SplashResultState.KaKaoLogin
            }
            .onEach {
                Timber.d("requestRefreshToken ${it.accessToken}, ${it.accessTokenExpiresIn}, ${it.refreshToken}")
                //saveTokenUseCase(it.accessToken, it.accessTokenExpiresIn, it.refreshToken)
                saveTokenUseCase(
                    "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjc0ODEyMjk5LCJleHAiOjE2ODA4MTcwOTl9.8buxTCLp_erwERq7d96AORKCyzbLNaqhg7ozNFKs0_M",
                    Long.MAX_VALUE, ""
                )
                _splashResultState.value = SplashResultState.BuddyCon
            }
            .launchIn(viewModelScope)
    }
}