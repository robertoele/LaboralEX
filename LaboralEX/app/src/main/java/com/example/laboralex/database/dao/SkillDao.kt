package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Skill
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {
    @Insert
    suspend fun insertAll(vararg skills: Skill): List<Long>

    @Delete
    suspend fun delete(skill: Skill)

    @Query("SELECT * FROM Skill")
    suspend fun getAll(): List<Skill>

    @Query("SELECT * FROM Skill")
    fun getAllAsFlow(): Flow<List<Skill>>

    @Query("SELECT * FROM Skill WHERE id = :id")
    suspend fun getById(id: Long): List<Skill>
}