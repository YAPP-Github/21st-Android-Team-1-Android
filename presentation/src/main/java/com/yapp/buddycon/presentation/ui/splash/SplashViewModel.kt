package com.yapp.buddycon.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.usecase.GetInitInfoUseCase
import com.yapp.buddycon.domain.usecase.GetTokenUseCase
import com.yapp.buddycon.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getInitInfoUseCase: GetInitInfoUseCase,
    private val getTokenUseCase: GetTokenUseCase
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
        getInitInfoUseCase()
            .combine(getTokenUseCase()) { initInfo, tokenInfo ->
                if(initInfo){
                    val (token, expiration) = tokenInfo
                    val currentTime = System.currentTimeMillis()

                    if(token.isEmpty() && expiration == 0L) _splashResultState.value = SplashResultState.KaKaoLogin
                    else if(expiration < currentTime) _splashResultState.value = SplashResultState.KaKaoLogin
                    else _splashResultState.value = SplashResultState.BuddyCon
                }else{
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
}