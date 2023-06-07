package com.capstone.posturku.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.posturku.data.room.history.HistoryDb
import com.capstone.posturku.data.room.history.HistoryDbDao
import com.capstone.posturku.data.room.profile.ProfileDb
import com.capstone.posturku.data.room.profile.ProfileDbDao

@Database(entities = [ArticleDb::class, HistoryDb::class, ProfileDb::class], version = 1)
abstract class ArticleDbRoomDatabase : RoomDatabase() {
    abstract fun artcileDbDao(): ArticleDbDao
    abstract fun historyDbDao(): HistoryDbDao
    abstract fun profileDbDao(): ProfileDbDao

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