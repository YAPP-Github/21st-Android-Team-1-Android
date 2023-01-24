package com.yapp.buddycon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.yapp.buddycon.domain.repository.InitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : InitRepository {
    override fun getInitInfo(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[BUDDYCON_INIT] ?: false
        }

    override suspend fun saveInitInfo() {
        dataStore.edit { preference ->
            Timber.d("saveInitInfo")
            preference[BUDDYCON_INIT] = true
        }
    }

    companion object {
        val BUDDYCON_INIT = booleanPreferencesKey("BUDDYCON_INIT")
    }
}