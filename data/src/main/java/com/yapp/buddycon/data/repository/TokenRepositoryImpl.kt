package com.yapp.buddycon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {
    override fun getToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[BUDDYCON_TOKEN] ?: ""
        }


    override suspend fun saveToken(accessToken: String) {
        dataStore.edit { preference ->
            Timber.d("saveToken $accessToken")
            preference[BUDDYCON_TOKEN] = accessToken
        }
    }

    companion object {
        val BUDDYCON_TOKEN = stringPreferencesKey("BUDDYCON_TOKEN")
    }
}