package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Course(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("reference") val reference: String,
    @ColumnInfo("progress") val progress: Int,
    @PrimaryKey(true) val id: Long = 0
)