package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.LoginRepositoryImpl
import com.yapp.buddycon.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository
}