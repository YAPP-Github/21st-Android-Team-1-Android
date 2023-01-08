package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.BuildConfig
import com.yapp.buddycon.data.network.BuddyConInterceptorQualifier
import com.yapp.buddycon.data.network.HttpLoggingInterceptorQualifier
import com.yapp.buddycon.data.network.LoginInterceptorQualifier
import com.yapp.buddycon.data.network.interceptor.BuddyConInterceptor
import com.yapp.buddycon.data.network.interceptor.LoginInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @HttpLoggingInterceptorQualifier
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @LoginInterceptorQualifier
    @Provides
    @Singleton
    fun provideServiceInterceptor(): Interceptor = LoginInterceptor()

    @BuddyConInterceptorQualifier
    @Provides
    @Singleton
    fun provideBuddyConInterceptor(): Interceptor = BuddyConInterceptor()
}