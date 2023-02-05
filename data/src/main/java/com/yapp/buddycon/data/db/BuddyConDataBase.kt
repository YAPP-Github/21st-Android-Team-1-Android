package com.yapp.buddycon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yapp.buddycon.data.db.dao.GiftconDao
import com.yapp.buddycon.data.db.entity.GiftconEntity

@Database(entities = [GiftconEntity::class], version = 1)
abstract class BuddyConDataBase : RoomDatabase(){
    abstract fun giftconDao(): GiftconDao
}