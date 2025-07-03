package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.laboralex.database.entity.course.Course
import com.example.laboralex.database.entity.course.CourseWithSkills
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert
    suspend fun insert(course: Course): Long

    @Delete
    suspend fun delete(course: Course)

    @Query("SELECT * FROM Course")
    fun getAsFlow(): Flow<List<Course>>

    @Transaction
    @Query("SELECT * FROM Course")
    fun getAllWithSkills(): Flow<List<CourseWithSkills>>
}