package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySkill
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {
    @Insert
    suspend fun insertAll(vararg companies: Company): List<Long>

    @Insert
    suspend fun insert(company: Company): Long

    @Delete
    suspend fun delete(company: Company)

    @Query("SELECT * FROM Company")
    suspend fun getAll(): List<Company>

    @Query("SELECT * FROM Company WHERE companyId = :id")
    suspend fun getById(id: Long): Company

    @Query("SELECT * FROM Company")
    fun getCompaniesAsFlow(): Flow<List<Company>>

    @Query("SELECT * FROM CompanySkill WHERE companyId = :id")
    fun getCompanySkills(id: Long): List<CompanySkill>
}