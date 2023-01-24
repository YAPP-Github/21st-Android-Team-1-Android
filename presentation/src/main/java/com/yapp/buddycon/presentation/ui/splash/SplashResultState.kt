package com.yapp.buddycon.presentation.ui.splash

sealed class SplashResultState {
    object Splash : SplashResultState()
    object WalkThrough : SplashResultState()
    object KaKaoLogin : SplashResultState()
    object BuddyCon : SplashResultState()
}