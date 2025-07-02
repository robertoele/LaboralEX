package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.CourseSkill
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseSkillDao {
    @Insert
    fun insert(courseSkill: CourseSkill)

    @Delete
    fun delete(courseSkill: CourseSkill)

    @Query("SELECT * FROM CourseSkill")
    fun getAllAsFlow(): Flow<List<CourseSkill>>
}