package com.capstone.posturku.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.posturku.model.LoginResult
import com.capstone.posturku.model.ProfileModel
import com.capstone.posturku.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[PASSWORD_KEY] ?:"",
                preferences[STATE_KEY] ?: false,
                preferences[TOKEN_KEY] ?: ""
            )
        }
    }

    fun getProfile(): Flow<ProfileModel>{
        return dataStore.data.map { preferences ->
            ProfileModel(
                preferences[ABOUTME_KEY] ?:"-",

                preferences[NAME_KEY] ?:"",
                preferences[PHONE_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[ADDRESS_KEY] ?:"",

                preferences[SKILL_KEY] ?:"",
                preferences[HOBBY_KEY] ?:"",
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun updateProfile(p: ProfileModel){
        dataStore.edit { preferences ->
            preferences[ABOUTME_KEY] = p.aboutMe
            preferences[NAME_KEY] = p.name
            preferences[PHONE_KEY] = p.phone
            preferences[ADDRESS_KEY] = p.address
            preferences[SKILL_KEY] = p.skill
            preferences[HOBBY_KEY] = p.hobby
        }
    }

    suspend fun login(result: LoginResult) {
        dataStore.edit { preferences ->
//            preferences[NAME_KEY] = result.name
            preferences[STATE_KEY] = true
            preferences[TOKEN_KEY] = "Bearer ${result.token}"
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences[TOKEN_KEY] = ""
        }
    }

    fun getToken(): String {
        return runBlocking {
            dataStore.data.first()[TOKEN_KEY] ?: ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val ABOUTME_KEY = stringPreferencesKey("aboutme")

        private val NAME_KEY = stringPreferencesKey("name")
        private val PHONE_KEY = stringPreferencesKey("phone")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ADDRESS_KEY = stringPreferencesKey("address")

        private val SKILL_KEY = stringPreferencesKey("skill")
        private val HOBBY_KEY = stringPreferencesKey("hobby")

        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}