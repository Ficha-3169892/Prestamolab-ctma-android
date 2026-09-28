package com.ctma.prestamolab.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val PRESTATARIO_DEFAULT_KEY = stringPreferencesKey("prestatario_default")
        val AMBIENTE_DEFAULT_KEY = stringPreferencesKey("ambiente_default")
        val SESSION_EMAIL_KEY = stringPreferencesKey("session_email")
        val SESSION_ROLE_KEY = stringPreferencesKey("session_role") // "APRENDIZ" o "ADMIN"
    }

    val prestatarioDefault: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PRESTATARIO_DEFAULT_KEY] ?: ""
        }

    val ambienteDefault: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[AMBIENTE_DEFAULT_KEY] ?: ""
        }

    val sessionEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SESSION_EMAIL_KEY]
        }

    val sessionRole: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SESSION_ROLE_KEY]
        }

    suspend fun guardarPreferencias(prestatario: String, ambiente: String) {
        context.dataStore.edit { preferences ->
            preferences[PRESTATARIO_DEFAULT_KEY] = prestatario
            preferences[AMBIENTE_DEFAULT_KEY] = ambiente
        }
    }

    suspend fun guardarSesion(email: String, role: String) {
        context.dataStore.edit { preferences ->
            preferences[SESSION_EMAIL_KEY] = email
            preferences[SESSION_ROLE_KEY] = role
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { preferences ->
            preferences.remove(SESSION_EMAIL_KEY)
            preferences.remove(SESSION_ROLE_KEY)
        }
    }
}
