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
    suspend fun insertAl(giftcons: List<GiftconEntity>)

    @Query("SELECT * FROM giftcon ORDER BY :sort ASC")
    fun getGiftconByASC(sort: String): PagingSource<Int, GiftconEntity>

    @Query("SELECT * FROM giftcon ORDER BY :sort DESC")
    fun getGiftconByDESC(sort: String)

    @Delete
    suspend fun delete(giftcon: GiftconEntity)
}