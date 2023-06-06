package com.capstone.posturku.data.room.history

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "HistoryDb")
@Parcelize
data class HistoryDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "startTime")
    var startTime: Long = 0,

    @ColumnInfo(name = "endTime")
    var endTime: Long = 0,

    @ColumnInfo(name = "durationBad")
    var durationBad: Int = 0,

    @ColumnInfo(name = "durationGood")
    var durationGood: Int = 0
) : Parcelable