package com.yapp.buddycon.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yapp.buddycon.data.db.entity.CouponEntity

@Dao
interface CouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coupons: List<CouponEntity>)

    @Query("SELECT * FROM coupon WHERE usable = :usable ORDER BY expireDate")
    fun getCouponByExpireDate(usable: Boolean): PagingSource<Int, CouponEntity>

    @Query("SELECT * FROM coupon WHERE usable = :usable ORDER BY name")
    fun getCouponByName(usable: Boolean): PagingSource<Int, CouponEntity>

    @Query("SELECT * FROM coupon WHERE usable = :usable ORDER BY createdAt")
    fun getCouponByCreatedAt(usable: Boolean): PagingSource<Int, CouponEntity>

    @Query("DELETE FROM coupon")
    suspend fun clearCoupon()
}