package com.example.laboralex.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppStateRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    data class AppState(val formMade: Boolean)

    private object PreferencesKeys {
        val FORM_MADE = booleanPreferencesKey("form_made")
    }

    val formMadeFlow: Flow<AppState> = dataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences())
        else throw exception
    }.map { preferences ->
        val formMade = preferences[PreferencesKeys.FORM_MADE] ?: false
        AppState(formMade)
    }

    suspend fun updateFormMade(formMade: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FORM_MADE] = formMade
        }
    }
}