package com.capstone.posturku.data.room.profile

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ProfileDb")
@Parcelize
data class ProfileDb(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    var id: Int? = null,

    @ColumnInfo(name = "aboutMe")
    var aboutMe: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "phone")
    var phone: String = "",

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "address")
    var address: String = "",

    @ColumnInfo(name = "skill")
    var skill: String = "",

    @ColumnInfo(name = "hobby")
    var hobby: String = "",
) : Parcelable