package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "first_surname") val firstSurname: String?,
    @ColumnInfo(name = "second_surname") val secondSurname: String?,
    @ColumnInfo(name = "profile_picture") val profilePictureId: Int?
)
