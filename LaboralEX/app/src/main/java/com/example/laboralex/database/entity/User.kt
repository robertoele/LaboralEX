package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "surnames") val surnames: String?,
    @ColumnInfo(name = "profile_picture") val profilePictureId: Long?
)
