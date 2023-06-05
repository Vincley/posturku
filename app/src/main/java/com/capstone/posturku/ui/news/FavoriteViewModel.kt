package com.capstone.posturku.ui.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.posturku.data.repository.FavoriteRepository
import com.capstone.posturku.data.room.ArticleDb

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(note: ArticleDb) {
        mNoteRepository.insert(note)
    }

    fun update(note: ArticleDb) {
        mNoteRepository.update(note)
    }

    fun delete(note: ArticleDb) {
        mNoteRepository.delete(note)
    }

    fun getAllNotes(): LiveData<List<ArticleDb>> = mNoteRepository.getAllNotes()

    fun getFavoriteByUrl(url: String?): LiveData<ArticleDb> {
        return mNoteRepository.getFavoriteUserByUsername(url)
    }
}