package com.yapp.buddycon.data.datasource.remote.addcoupon

import com.yapp.buddycon.data.network.api.AddCouponService
import javax.inject.Inject

class AddCouponRemoteDataSourceImpl @Inject constructor(
    private val addCouponService: AddCouponService
)  : AddCouponRemoteDataSource {
}