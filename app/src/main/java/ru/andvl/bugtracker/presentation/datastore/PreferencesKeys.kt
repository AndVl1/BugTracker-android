package ru.andvl.bugtracker.presentation.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    private const val LOGGED_IN_STRING = "is_logged_in"
    private const val CURRENT_USER_ID_STRING = "current_user_id"
    private const val CURRENT_USER_NAME_STRING = "current_user_name"
    val IS_LOGGED_IN = booleanPreferencesKey(LOGGED_IN_STRING)
    val CURRENT_USER_ID = intPreferencesKey(CURRENT_USER_ID_STRING)
    val CURRENT_USER_NAME = stringPreferencesKey(CURRENT_USER_NAME_STRING)
}
