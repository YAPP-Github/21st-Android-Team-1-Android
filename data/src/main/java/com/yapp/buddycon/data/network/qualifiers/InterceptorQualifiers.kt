package com.yapp.buddycon.data.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpLoggingInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuddyConInterceptorQualifier