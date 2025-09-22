package com.example.weathernow.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DS_NAME = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DS_NAME)

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val DARK = booleanPreferencesKey("dark_mode")
        val LANG = stringPreferencesKey("lang")        // "en" | "el"
        val LAST_CITY = stringPreferencesKey("last_city")
    }

    val darkFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.DARK] ?: false }
    val langFlow: Flow<String> = context.dataStore.data.map { it[Keys.LANG] ?: "en" }
    val lastCityFlow: Flow<String> = context.dataStore.data.map { it[Keys.LAST_CITY] ?: "" }

    suspend fun setDark(enabled: Boolean) = context.dataStore.edit { it[Keys.DARK] = enabled }
    suspend fun setLang(lang: String)     = context.dataStore.edit { it[Keys.LANG] = lang }
    suspend fun setLastCity(city: String) = context.dataStore.edit { it[Keys.LAST_CITY] = city }
}
