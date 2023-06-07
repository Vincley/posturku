package com.capstone.posturku.data.preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.posturku.model.ProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfilePreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getProfile(): Flow<ProfileModel> {
        return dataStore.data.map { preferences ->
            ProfileModel(
                preferences[ProfilePreference.ABOUTME_KEY] ?:"",
                preferences[ProfilePreference.NAME_KEY] ?:"",
                preferences[ProfilePreference.PHONE_KEY] ?:"",
                preferences[ProfilePreference.EMAIL_KEY] ?: "",
                preferences[ProfilePreference.ADDRESS_KEY] ?: "",
                preferences[ProfilePreference.SKILL_KEY] ?: "",
                preferences[ProfilePreference.HOBBY_KEY] ?: "",
            )
        }
    }

    suspend fun SetAboutMe(result: String) {
        dataStore.edit { preferences ->
            preferences[ProfilePreference.ABOUTME_KEY] = result
        }
    }

    suspend fun SetContact(name: String, phone: String, address: String) {
        dataStore.edit { preferences ->
            preferences[ProfilePreference.NAME_KEY] = name
            preferences[ProfilePreference.PHONE_KEY] = phone
            preferences[ProfilePreference.ADDRESS_KEY] = address
        }
    }

    suspend fun SetEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[ProfilePreference.EMAIL_KEY] = email
        }
    }

    suspend fun SetInterest(skill: String, hobby: String) {
        dataStore.edit { preferences ->
            preferences[ProfilePreference.SKILL_KEY] = skill
            preferences[ProfilePreference.HOBBY_KEY] = hobby
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProfilePreference? = null

        private val ABOUTME_KEY = stringPreferencesKey("profileAboutMe")
        private val NAME_KEY = stringPreferencesKey("profileName")
        private val PHONE_KEY = stringPreferencesKey("profilePhone")
        private val EMAIL_KEY = stringPreferencesKey("profileEmail")
        private val ADDRESS_KEY = stringPreferencesKey("profileAddress")
        private val SKILL_KEY = stringPreferencesKey("profileSkill")
        private val HOBBY_KEY = stringPreferencesKey("profileHobby")

        fun getInstance(dataStore: DataStore<Preferences>): ProfilePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ProfilePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
