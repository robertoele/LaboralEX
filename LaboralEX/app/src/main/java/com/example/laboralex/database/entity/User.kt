package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "surname1") val surname1: String?,
    @ColumnInfo(name = "surname2") val surname2: String?,
    @ColumnInfo(name = "profile_picture") val profilePictureId: Int?
)
