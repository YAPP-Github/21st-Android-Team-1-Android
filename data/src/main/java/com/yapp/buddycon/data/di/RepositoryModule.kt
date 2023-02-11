package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.BootRepositoryImpl
import com.yapp.buddycon.data.repository.CouponRepositoryImpl
import com.yapp.buddycon.data.repository.TokenRepositoryImpl
import com.yapp.buddycon.data.repository.UserRepositoryImpl
import com.yapp.buddycon.domain.repository.*
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
    fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    fun bindsTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    fun bindsBootRepository(
        bootRepositoryImpl: BootRepositoryImpl
    ): BootRepository

    @Binds
    @Singleton
    fun bindsCouponRepository(
        couponRepositoryImpl: CouponRepositoryImpl
    ): CouponRepository
}