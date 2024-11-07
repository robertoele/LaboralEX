package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.CompanySpeciality

@Dao
interface CompanySpecialityDao {
    @Insert
    suspend fun insertAll(vararg companies: CompanySpeciality)

    @Delete
    suspend fun delete(company: CompanySpeciality)

    @Query("SELECT * FROM CompanySpeciality")
    suspend fun getAll(): List<CompanySpeciality>

    @Query("SELECT * FROM CompanySpeciality WHERE company_id = :id")
    suspend fun getByCompanyId(id: Int): List<CompanySpeciality>

    @Query("SELECT * FROM CompanySpeciality WHERE speciality_id = :id")
    suspend fun getBySpecialityId(id: Int): List<CompanySpeciality>
}