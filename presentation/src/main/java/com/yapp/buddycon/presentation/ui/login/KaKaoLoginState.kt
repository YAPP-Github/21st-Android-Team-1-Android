package com.yapp.buddycon.presentation.ui.login

import com.yapp.buddycon.domain.model.UserInfo

sealed class KaKaoLoginState {
    object LogOut : KaKaoLoginState()
    data class Login(val userInfo: UserInfo?) : KaKaoLoginState()
    data class Error(val throwable: Throwable?) : KaKaoLoginState()
}