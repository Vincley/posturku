package com.capstone.posturku.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.posturku.data.repository.room.HistoryRepoRoom
import com.capstone.posturku.data.room.history.HistoryDb

class HistoryViewModel(application: Application) : ViewModel() {
    private val mRepository: HistoryRepoRoom = HistoryRepoRoom(application)

    fun insert(note: HistoryDb) {
        mRepository.insert(note)
    }

    fun update(note: HistoryDb) {
        mRepository.update(note)
    }

    fun delete(note: HistoryDb) {
        mRepository.delete(note)
    }

    fun getAllHistories(): LiveData<List<HistoryDb>> = mRepository.getAllHistories()
    fun getHistoriesOneMonth(): LiveData<List<HistoryDb>> = mRepository.getHistoriesOneMonth()

    fun getHistoriesByStartTime(startTime: Long): LiveData<HistoryDb> {
        return mRepository.getHistoriesByStartTime(startTime)
    }
}