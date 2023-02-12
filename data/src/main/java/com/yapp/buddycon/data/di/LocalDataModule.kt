package com.yapp.buddycon.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.yapp.buddycon.data.db.BuddyConDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    const val BUDDYCON_DATASTORE = "BUDDYCON_DATASTORE"
    const val BUDDYCON_DATABASE = "BUDDYCON_DATABASE"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(BUDDYCON_DATASTORE)
        }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BuddyConDataBase = Room.databaseBuilder(
        context,
        BuddyConDataBase::class.java,
        BUDDYCON_DATABASE
    ).build()
}