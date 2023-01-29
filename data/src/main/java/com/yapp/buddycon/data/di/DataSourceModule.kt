package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSourceImpl
import com.yapp.buddycon.data.datasource.remote.token.RefreshTokenRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.token.RefreshTokenRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsLoginRemoteDataSource(
        loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl
    ): LoginRemoteDataSource

    @Binds
    fun bindsRefreshTokenRemoteDataSource(
        refreshTokenRemoteDataSourceImpl: RefreshTokenRemoteDataSourceImpl
    ): RefreshTokenRemoteDataSource
}