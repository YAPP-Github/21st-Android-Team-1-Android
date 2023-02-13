package com.yapp.buddycon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yapp.buddycon.data.db.dao.CouponDao
import com.yapp.buddycon.data.db.dao.CouponRemoteKeysDao
import com.yapp.buddycon.data.db.entity.CouponEntity
import com.yapp.buddycon.data.db.entity.CouponRemoteKeysEntity


@Database(entities = [CouponEntity::class, CouponRemoteKeysEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class BuddyConDataBase : RoomDatabase() {
    abstract fun couponDao(): CouponDao
    abstract fun couponRemoteKeysDao(): CouponRemoteKeysDao
}