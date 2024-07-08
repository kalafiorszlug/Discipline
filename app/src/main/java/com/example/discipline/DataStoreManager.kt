package com.example.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.discipline.NavigationItem
import com.example.discipline.Reward
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val THEME_KEY = stringPreferencesKey("theme")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TASKS_TITLES_KEY = stringPreferencesKey("tasksTitles")
        private val TASKS_PAYOFF_KEY = stringPreferencesKey("tasksPayoff")
        private val TASKS_DEADLINES_KEY = stringPreferencesKey("tasksDeadlines")
        private val TASKS_LISTS_BELONGING_KEY = stringPreferencesKey("tasksListsBelonging")
        private val TASKS_LISTS_KEY = stringPreferencesKey("tasksListsKey")
        private val TASKS_ORDER_CRITERIA_KEY = stringPreferencesKey("tasksOrderCriteriaKey")
        private val REWARDS_KEY = stringPreferencesKey("rewards")
        private val HAS_USER_LOGGED_IN_KEY = stringPreferencesKey("login")
        private val CREDITS_KEY = stringPreferencesKey("credits")
    }

    val getTheme: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: ""
    }

    val getUsername: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    val getTasksOrderCirteria: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[TASKS_ORDER_CRITERIA_KEY] ?: ""
    }

    val getLoginInformation: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[HAS_USER_LOGGED_IN_KEY] ?: ""
    }

    val getCredits: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CREDITS_KEY] ?: ""
    }

    val getTasksTitles: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[TASKS_TITLES_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }

    val getTasksPayoff: Flow<List<Int>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[TASKS_PAYOFF_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<Int>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }

    val getTasksDeadlines: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[TASKS_DEADLINES_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }

    val getTasksListBelonging: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[TASKS_LISTS_BELONGING_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }

    val getTasksLists: Flow<List<NavigationItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[TASKS_LISTS_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<NavigationItem>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
        }

    val getRewards: Flow<List<Reward>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[REWARDS_KEY] ?: ""
            if (jsonString.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<List<Reward>>() {}.type
                gson.fromJson(jsonString, type)
            } else {
                emptyList()
            }
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

    suspend fun saveTasksOrderCriteria(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TASKS_ORDER_CRITERIA_KEY] = token
        }
    }

    suspend fun saveLoginInformation(token: String) {
        context.dataStore.edit { preferences ->
            preferences[HAS_USER_LOGGED_IN_KEY] = token
        }
    }

    suspend fun saveCredits(token: String) {
        context.dataStore.edit { preferences ->
            preferences[CREDITS_KEY] = token
        }
    }

    suspend fun saveTasksTitles(list: List<String>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[TASKS_TITLES_KEY] = jsonString
        }
    }

    suspend fun saveTasksPayoff(list: List<Int>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[TASKS_PAYOFF_KEY] = jsonString
        }
    }

    suspend fun saveTasksDeadlines(list: List<String>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[TASKS_DEADLINES_KEY] = jsonString
        }
    }

    suspend fun saveTasksListBelonging(list: List<String>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        context.dataStore.edit { preferences ->
            preferences[TASKS_LISTS_BELONGING_KEY] = jsonString
        }
    }

    suspend fun saveTasksLists(navigationItem: List<NavigationItem>) {
        val gson = Gson()
        val jsonString = gson.toJson(navigationItem)
        context.dataStore.edit { preferences ->
            preferences[TASKS_LISTS_KEY] = jsonString
        }
    }

    suspend fun saveRewards(reward: List<Reward>) {
        val gson = Gson()
        val jsonString = gson.toJson(reward)
        context.dataStore.edit { preferences ->
            preferences[REWARDS_KEY] = jsonString
        }
    }

}