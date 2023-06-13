package com.capstone.posturku.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.capstone.posturku.data.preferences.UserPreference
import com.capstone.posturku.data.repository.room.ProfileRepoRoom
import com.capstone.posturku.data.room.profile.ProfileDb
import com.capstone.posturku.model.ProfileModel
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : ViewModel(){

    private val mRepository: ProfileRepoRoom = ProfileRepoRoom(application)

    fun getProfile(): LiveData<ProfileDb> {
        return mRepository.getProfile()
    }

    fun updateProfile(data: ProfileModel) {
        val a = convertTo(data)
        mRepository.setData(a)
    }

    private fun convertTo(data: ProfileModel) : ProfileDb{
        return  ProfileDb(
            aboutMe = data.aboutMe,
            name = data.name,
            phone = data.phone,
            email = data.email,
            address = data.address,
            skill = data.skill,
            hobby = data.hobby
        )
    }

    private fun convertTo(data: ProfileDb) : ProfileModel{
        return  ProfileModel(
            aboutMe = data.aboutMe,
            name = data.name,
            phone = data.phone,
            email = data.email,
            address = data.address,
            skill = data.skill,
            hobby = data.hobby
        )
    }
}

class ProfileViewModelV2(private val pref: UserPreference) : ViewModel() {
    fun getProfile(): LiveData<ProfileModel> {
        return pref.getProfile().asLiveData()
    }

    fun updateProfile(result: ProfileModel) {
        viewModelScope.launch {
            pref.updateProfile(result)
        }
    }
}