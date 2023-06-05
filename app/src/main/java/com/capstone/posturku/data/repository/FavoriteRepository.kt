package com.capstone.posturku.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.posturku.data.room.ArticleDb
import com.capstone.posturku.data.room.ArticleDbDao
import com.capstone.posturku.data.room.ArticleDbRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mNotesDao: ArticleDbDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ArticleDbRoomDatabase.getDatabase(application)
        mNotesDao = db.artcileDbDao()
    }

    fun getAllNotes(): LiveData<List<ArticleDb>> = mNotesDao.getAllFavorite()
    fun getFavoriteUserByUsername(url: String?): LiveData<ArticleDb> = mNotesDao.getFavoriteUserByUsername(url)



    fun insert(note: ArticleDb) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: ArticleDb) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: ArticleDb) {
        executorService.execute { mNotesDao.update(note) }
    }


}