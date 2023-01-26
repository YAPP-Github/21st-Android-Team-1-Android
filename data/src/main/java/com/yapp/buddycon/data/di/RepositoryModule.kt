package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.InitRepositoryImpl
import com.yapp.buddycon.data.repository.LoginRepositoryImpl
import com.yapp.buddycon.data.repository.TokenRepositoryImpl
import com.yapp.buddycon.domain.repository.InitRepository
import com.yapp.buddycon.domain.repository.LoginRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    fun bindsTokenRepository(
        tokenRepository: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    fun bindsInitRepository(
        initRepository: InitRepositoryImpl
    ): InitRepository
}