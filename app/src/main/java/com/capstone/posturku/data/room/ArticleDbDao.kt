package com.capstone.posturku.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDbDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorites: ArticleDb)

    @Update
    fun update(favorites: ArticleDb)

    @Delete
    fun delete(favorites: ArticleDb)

    @Query("SELECT * from ArticleDb ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<ArticleDb>>

    @Query("SELECT * from ArticleDb Where url=:url")
    fun getFavoriteUserByUsername(url: String?): LiveData<ArticleDb>
}