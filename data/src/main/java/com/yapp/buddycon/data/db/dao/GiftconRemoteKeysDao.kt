package com.yapp.buddycon.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yapp.buddycon.data.db.entity.GiftconRemoteKeysEntity

@Dao
interface GiftconRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAl(remoteKeys: List<GiftconRemoteKeysEntity>)

    @Query("SELECT * FROM giftcon_remote_keys WHERE id = :id")
    suspend fun getGiftconRemoteKey(id: Int): GiftconRemoteKeysEntity

    @Query("DELETE FROM giftcon_remote_keys")
    suspend fun clearGiftconRemoteKeys()
}