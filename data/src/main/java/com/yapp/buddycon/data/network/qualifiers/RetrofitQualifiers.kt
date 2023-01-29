package com.yapp.buddycon.data.network.qualifiers

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuddyConRetrofit