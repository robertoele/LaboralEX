package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.CompanySkill
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanySkillDao {
    @Insert
    suspend fun insertAll(vararg companies: CompanySkill)

    @Delete
    suspend fun delete(company: CompanySkill)

    @Query("SELECT * FROM CompanySkill")
    suspend fun getAll(): List<CompanySkill>

    @Query("SELECT * FROM CompanySkill")
    fun getAllAsFlow(): Flow<List<CompanySkill>>

    @Query("SELECT * FROM CompanySkill WHERE company_id = :id")
    suspend fun getByCompanyId(id: Long): List<CompanySkill>

    @Query("SELECT * FROM CompanySkill WHERE skill_id = :id")
    suspend fun getBySkillId(id: Long): List<CompanySkill>

}