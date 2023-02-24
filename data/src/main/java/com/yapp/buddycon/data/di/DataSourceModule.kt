package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.datasource.remote.addcoupon.AddCouponRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.addcoupon.AddCouponRemoteDataSourceImpl
import com.yapp.buddycon.data.datasource.remote.coupon.ChangeCouponRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.coupon.ChangeCouponRemoteDataSourceImpl
import com.yapp.buddycon.data.datasource.remote.coupon.GetCustomCouponDetailRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.coupon.GetCustomCouponDetailRemoteDataSourceImpl
import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.login.LoginRemoteDataSourceImpl
import com.yapp.buddycon.data.datasource.remote.makecon.GetGiftConDetailRemoteDataSource
import com.yapp.buddycon.data.datasource.remote.makecon.GetGiftConDetailRemoteDataSourceImpl
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
    fun bindsAddCouponRemoteDataSource(
        addCouponRemoteDataSourceImpl: AddCouponRemoteDataSourceImpl
    ): AddCouponRemoteDataSource

    @Binds
    fun bindsGiftConDetailRemoteDataSource(
        getGiftConDetailRemoteDataSourceImpl: GetGiftConDetailRemoteDataSourceImpl
    ): GetGiftConDetailRemoteDataSource

    @Binds
    fun bindsChangeCouponRemoteDataSource(
        changeCouponRemoteDataSourceImpl: ChangeCouponRemoteDataSourceImpl
    ): ChangeCouponRemoteDataSource

    @Binds
    fun bindsGetCustomCouponDetailRemoteDataSource(
        getCustomCouponDetailRemoteDataSourceImpl: GetCustomCouponDetailRemoteDataSourceImpl
    ): GetCustomCouponDetailRemoteDataSource
}