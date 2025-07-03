package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.course.CourseSkill
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseSkillDao {
    @Insert
    suspend fun insert(courseSkill: CourseSkill): Long

    @Insert
    suspend fun insertAll(vararg courseSkills: CourseSkill): List<Long>

    @Delete
    suspend fun delete(courseSkill: CourseSkill)

    @Query("SELECT * FROM CourseSkill")
    fun getAllAsFlow(): Flow<List<CourseSkill>>
}