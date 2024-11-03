package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Speciality(
    @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String
)
