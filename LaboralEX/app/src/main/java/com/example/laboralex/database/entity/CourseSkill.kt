package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["courseId", "skillId"])
data class CourseSkill(
    @ColumnInfo val courseId: Long,
    @ColumnInfo val skillId: Long
)
