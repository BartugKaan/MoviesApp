package com.example.moviesapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class AppPref(var context: Context) {
    companion object {
        val USERNAME_KEY = stringPreferencesKey("USERNAME")
        val ONBOARDING_KEY = booleanPreferencesKey("IS_ONBOARDING_SEEN")
        private val Context.ds: DataStore<Preferences> by preferencesDataStore("appInfo")
    }

    suspend fun saveUserName(userName: String) {
        context.ds.edit {
            it[USERNAME_KEY] = userName
        }
    }

    suspend fun getUserName(): String {
        val p = context.ds.data.first()
        return p[USERNAME_KEY] ?: "Username could not found!"
    }

    suspend fun deleteUserName() {
        context.ds.edit {
            it.remove(USERNAME_KEY)
        }
    }

    suspend fun saveOnboardingPreferences(isSeen: Boolean) {
        context.ds.edit {
            it[ONBOARDING_KEY] = isSeen
        }
    }

    suspend fun getOnboardingPreferences(): Boolean {
        val p = context.ds.data.first()
        return p[ONBOARDING_KEY] ?: false
    }

    suspend fun deleteOnboardingPreferences() {
        context.ds.edit {
            it.remove(ONBOARDING_KEY)
        }
    }
}