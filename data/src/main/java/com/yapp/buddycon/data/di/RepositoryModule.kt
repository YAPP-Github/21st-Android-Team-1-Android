package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.*
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

    @Binds
    @Singleton
    fun bindsAddCouponRepository(
        addCouponRepositoryImpl: AddCouponRepositoryImpl
    ): AddCouponRepository
}