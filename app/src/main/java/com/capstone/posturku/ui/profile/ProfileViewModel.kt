package com.capstone.posturku.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.capstone.posturku.data.repository.ProfileRepository
import com.capstone.posturku.data.room.profile.ProfileDb

class ProfileViewModel(application: Application) : ViewModel(){

    private val mRepository: ProfileRepository = ProfileRepository(application)

    fun getProfile(): LiveData<ProfileDb> {
        return mRepository.getProfile()
//        val allItems = mRepository.getAll().value // Get the value of LiveData<List<ProfileDb>>
//        val firstItem = allItems?.firstOrNull() // Get the first item from the list
//
//        val itemLiveData = MutableLiveData<ProfileDb>()
//
//        if(firstItem != null){
//            itemLiveData.value = firstItem!!
//        }
//        return itemLiveData
    }

    fun setData(data: ProfileDb) {
        mRepository.setData(data)
    }
}