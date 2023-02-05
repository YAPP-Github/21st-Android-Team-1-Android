package com.yapp.buddycon.data.repository

import com.yapp.buddycon.data.datasource.remote.addcoupon.AddCouponRemoteDataSource
import com.yapp.buddycon.domain.repository.AddCouponRepository
import javax.inject.Inject

class AddCouponRepositoryImpl @Inject constructor(
    private val addCouponRemoteDataSource: AddCouponRemoteDataSource
) : AddCouponRepository {
}