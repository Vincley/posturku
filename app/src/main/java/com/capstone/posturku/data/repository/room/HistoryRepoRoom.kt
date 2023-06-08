package com.capstone.posturku.data.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.posturku.data.room.ArticleDbRoomDatabase
import com.capstone.posturku.data.room.history.HistoryDb
import com.capstone.posturku.data.room.history.HistoryDbDao
import com.capstone.posturku.utils.converter.DateConverter
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class HistoryRepoRoom(application: Application) {
    private val mDataDao: HistoryDbDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ArticleDbRoomDatabase.getDatabase(application)
        mDataDao = db.historyDbDao()
    }

    fun getAllHistories(): LiveData<List<HistoryDb>> = mDataDao.GetAllHistories()
    fun getHistoriesByStartTime(startTime: Long): LiveData<HistoryDb> = mDataDao.getHistoriesByStartTime(startTime)
    fun getHistoriesOneMonth(): LiveData<List<HistoryDb>> {
        val currentDate = DateConverter.convertToEpochMilliseconds(Date())
        val oneMonthAgo = currentDate - TimeUnit.DAYS.toMillis(30)

        return mDataDao.getHistoriesByTimeRange(oneMonthAgo, currentDate)
    }

    fun insert(note: HistoryDb) {
        executorService.execute { mDataDao.insert(note) }
    }

    fun delete(note: HistoryDb) {
        executorService.execute { mDataDao.delete(note) }
    }

    fun update(note: HistoryDb) {
        executorService.execute { mDataDao.update(note) }
    }


}