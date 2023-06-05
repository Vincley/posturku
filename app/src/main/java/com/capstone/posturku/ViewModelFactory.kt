package com.capstone.posturku

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.posturku.data.UserPreference
import com.capstone.posturku.data.repository.NewsRepository
import com.capstone.posturku.ui.login.LoginViewModel
import com.capstone.posturku.ui.main.MainViewModel
import com.capstone.posturku.ui.news.FavoriteViewModel
import com.capstone.posturku.ui.news.NewsViewModel
import com.capstone.posturku.ui.signup.SignupViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}


class ViewModelRepoFactory(private val repo : NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}

class ViewModelRoomFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelRoomFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelRoomFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelRoomFactory::class.java) {
                    INSTANCE = ViewModelRoomFactory(application)
                }
            }
            return INSTANCE as ViewModelRoomFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
//        else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
//            return NoteAddUpdateViewModel(mApplication) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}