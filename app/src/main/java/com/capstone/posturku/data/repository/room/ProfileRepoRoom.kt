package com.capstone.posturku.data.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.capstone.posturku.data.room.ArticleDbRoomDatabase
import com.capstone.posturku.data.room.profile.ProfileDb
import com.capstone.posturku.data.room.profile.ProfileDbDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProfileRepoRoom(application: Application) {
    private val mDataDao: ProfileDbDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ArticleDbRoomDatabase.getDatabase(application)
        mDataDao = db.profileDbDao()
    }

    fun getAll(): LiveData<List<ProfileDb>> = mDataDao.getAll()
    fun getProfile(): LiveData<ProfileDb> = mDataDao.getProfile()

    fun setData(note: ProfileDb){
        val allData: LiveData<List<ProfileDb>> = Transformations.map(getAll()) { profileList ->
            if (profileList.isNullOrEmpty()) {
                executorService.execute { mDataDao.insert(note) }
            } else {
                executorService.execute { mDataDao.update(note) }
            }
            profileList
        }

        val observer = object : Observer<List<ProfileDb>> {
            override fun onChanged(profileList: List<ProfileDb>?) {
                if (profileList != null) {
                    allData.removeObserver(this)
                }
            }
        }
        allData.observeForever(observer)

    }


}