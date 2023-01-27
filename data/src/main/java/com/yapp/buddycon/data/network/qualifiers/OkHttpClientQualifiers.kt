package com.yapp.buddycon.data.network

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuddyConClient