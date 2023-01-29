package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSourceImpl
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
}