package com.yapp.buddycon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.yapp.buddycon.domain.repository.BootRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class BootRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : BootRepository {
    override fun getBootInfo(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[BUDDYCON_BOOT_INFO] ?: false
        }

    override suspend fun saveBootInfo() {
        dataStore.edit { preference ->
            preference[BUDDYCON_BOOT_INFO] = true
        }
    }

    companion object {
        val BUDDYCON_BOOT_INFO = booleanPreferencesKey("BUDDYCON_BOOT_INFO")
    }
}