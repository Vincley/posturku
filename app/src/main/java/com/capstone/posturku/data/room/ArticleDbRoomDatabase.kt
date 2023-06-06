package com.capstone.posturku.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.posturku.data.room.history.HistoryDb
import com.capstone.posturku.data.room.history.HistoryDbDao

@Database(entities = [ArticleDb::class, HistoryDb::class], version = 1)
abstract class ArticleDbRoomDatabase : RoomDatabase() {

    abstract fun artcileDbDao(): ArticleDbDao
    abstract fun historyDbDao(): HistoryDbDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDbRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ArticleDbRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ArticleDbRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ArticleDbRoomDatabase::class.java, "posturku_database")
                        .build()
                }
            }
            return INSTANCE as ArticleDbRoomDatabase
        }
    }
}