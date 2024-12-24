package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.UserSkill

@Dao
interface UserSkillDao {
    @Insert
    suspend fun insertAll(vararg userSkills: UserSkill)

    @Delete
    suspend fun delete(company: UserSkill)

    @Query("SELECT * FROM UserSkill")
    suspend fun getAll(): List<UserSkill>

    @Query("SELECT * FROM UserSkill WHERE skillId = :id")
    suspend fun getBySkillId(id: Long): UserSkill

    @Query("SELECT * FROM UserSkill WHERE userId = :id")
    suspend fun getByUserId(id: Long): UserSkill
}