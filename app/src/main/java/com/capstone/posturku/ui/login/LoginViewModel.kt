package com.capstone.posturku.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.model.LoginResult
import com.capstone.posturku.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(result: LoginResult) {
        viewModelScope.launch {
            pref.login(result)
        }
    }
}