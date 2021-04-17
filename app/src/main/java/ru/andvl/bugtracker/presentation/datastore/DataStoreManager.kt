package ru.andvl.bugtracker.presentation.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber

private val Context.dataStore by preferencesDataStore("login")

class DataStoreManager(context: Context) {

    private val loginDataStore = context.dataStore

    val isLoggedIn: Flow<Int> = loginDataStore.data
        .map { settings ->
            settings[PreferencesKeys.IS_LOGGED_IN] ?: LoginStatus.NOT_LOGGED_IN
        }

    val isLoggedIn2 = runBlocking { loginDataStore.data
        .map { settings ->
            settings[PreferencesKeys.IS_LOGGED_IN] ?: LoginStatus.NOT_LOGGED_IN
        }
    }

    val currentUserId: Flow<Int> = loginDataStore.data
        .map { user ->
            user[PreferencesKeys.CURRENT_USER_ID] ?: -1
        }

    suspend fun setLoginStatus(isLoggedIn: Int) {
        loginDataStore.edit { settings ->
            settings[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
        }
        Timber.d("DATASTORE $isLoggedIn")
    }

    suspend fun setCurrentUserId(id: Int) {
        loginDataStore.edit { user ->
            user[PreferencesKeys.CURRENT_USER_ID] = id
        }
    }

}
