package com.capstone.posturku.data.room.profile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDbDao {
    @Insert
    fun insert(data: ProfileDb)

    @Update
    fun update(data: ProfileDb)

    @Delete
    fun delete(data: ProfileDb)

    @Query("SELECT * FROM ProfileDb LIMIT 1")
    fun getProfile(): LiveData<ProfileDb>

    @Query("SELECT * from ProfileDb")
    fun getAll(): LiveData<List<ProfileDb>>
}