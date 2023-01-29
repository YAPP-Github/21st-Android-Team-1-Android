package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.BuildConfig
import com.yapp.buddycon.data.network.BuddyConInterceptorQualifier
import com.yapp.buddycon.data.network.HttpLoggingInterceptorQualifier
import com.yapp.buddycon.data.network.interceptor.BuddyConInterceptor
import com.yapp.buddycon.data.repository.TokenRepositoryImpl
import com.yapp.buddycon.data.repository.UserRepositoryImpl
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

    @BuddyConInterceptorQualifier
    @Provides
    @Singleton
    fun provideBuddyConInterceptor(
        tokenRepositoryImpl: TokenRepositoryImpl,
        userRepositoryImpl: UserRepositoryImpl
    ): Interceptor = BuddyConInterceptor(tokenRepositoryImpl, userRepositoryImpl)
}