package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("name") val name: String
)
