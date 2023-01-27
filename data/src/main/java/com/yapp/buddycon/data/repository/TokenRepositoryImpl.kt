package com.yapp.buddycon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
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

    override fun getTokenExpiration(): Flow<Long> =
        dataStore.data.map { preference ->
            preference[BUDDYCON_TOKEN_EXPIRATION] ?: 0L
        }

    override fun getRefreshToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[BUDDYCON_REFRESH_TOKEN] ?: ""
        }

    override suspend fun saveToken(
        accessToken: String,
        accessTokenExpiresIn: Long,
        refreshToken: String
    ) {
        dataStore.edit { preference ->
            Timber.d("saveToken accessToken: $accessToken accessTokenExpiresIn: $accessTokenExpiresIn refreshToken: $refreshToken")
            preference[BUDDYCON_TOKEN] = accessToken
            preference[BUDDYCON_TOKEN_EXPIRATION] = accessTokenExpiresIn
            preference[BUDDYCON_REFRESH_TOKEN] = refreshToken
        }
    }

    companion object {
        val BUDDYCON_TOKEN = stringPreferencesKey("BUDDYCON_TOKEN")
        val BUDDYCON_TOKEN_EXPIRATION = longPreferencesKey("BUDDYCON_TOKEN_EXPIRATION")
        val BUDDYCON_REFRESH_TOKEN = stringPreferencesKey("BUDDYCON_REFRESH_TOKEN")
    }
}