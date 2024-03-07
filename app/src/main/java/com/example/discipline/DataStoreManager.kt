package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val THEME_KEY = stringPreferencesKey("theme")
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

    val getTheme: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: ""
    }

    val getUsername: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    suspend fun saveTheme(token: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = token
        }
    }

    suspend fun saveUsername(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = token
        }
    }
}