package com.capstone.posturku.data.room.history

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface HistoryDbDao {
    @Insert
    fun insert(favorites: HistoryDb)

    @Update
    fun update(favorites: HistoryDb)

    @Delete
    fun delete(favorites: HistoryDb)

    @Query("SELECT * from HistoryDb ORDER BY startTime Desc")
    fun GetAllHistories(): LiveData<List<HistoryDb>>

    @Query("SELECT * from HistoryDb Where startTime=:startTime")
    fun getHistoriesByStartTime(startTime: Long): LiveData<HistoryDb>

    @Query("SELECT * FROM HistoryDb WHERE startTime BETWEEN :startRange AND :endRange ORDER BY startTime DESC")
    fun getHistoriesByTimeRange(startRange: Long, endRange: Long): LiveData<List<HistoryDb>>

}