package com.example.laboralex.database.entity.course

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("reference") val reference: String? = null,
    @ColumnInfo("finished") var finished: Boolean = false,
    @PrimaryKey(true) val courseId: Long = 0
)