package com.yapp.buddycon.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yapp.buddycon.data.db.entity.GiftconEntity

@Dao
interface GiftconDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(giftcons: List<GiftconEntity>)

    @Query("SELECT * FROM giftcon WHERE usable = :usable ORDER BY expireDate")
    fun getGiftconByExpireDate(usable: Boolean): PagingSource<Int, GiftconEntity>

    @Query("SELECT * FROM giftcon WHERE usable = :usable ORDER BY name")
    fun getGiftconByName(usable: Boolean): PagingSource<Int, GiftconEntity>

    //@Query("SELECT * FROM giftcon WHERE usable = :usable ORDER BY createdAt")
    //fun getGiftconByCreatedAt(usable: Boolean): PagingSource<Int, GiftconEntity>

    @Query("DELETE FROM giftcon")
    suspend fun clearGiftcon()
}