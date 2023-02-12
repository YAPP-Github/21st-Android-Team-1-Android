package com.yapp.buddycon.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yapp.buddycon.data.db.entity.CouponRemoteKeysEntity

@Dao
interface CouponRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<CouponRemoteKeysEntity>)

    @Query("SELECT * FROM coupon_remote_keys WHERE id = :id")
    suspend fun getCouponRemoteKey(id: Int): CouponRemoteKeysEntity

    @Query("DELETE FROM coupon_remote_keys")
    suspend fun clearCouponRemoteKeys()
}