package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.CompanySpeciality

@Dao
interface CompanySpecialityDao {
    @Insert
    fun insertAll(vararg companies: CompanySpeciality)

    @Delete
    fun delete(company: CompanySpeciality)

    @Query("SELECT * FROM CompanySpeciality")
    fun getAll(): List<CompanySpeciality>

    @Query("SELECT * FROM CompanySpeciality WHERE company_id = :id")
    fun getByCompanyId(id: Int): CompanySpeciality

    @Query("SELECT * FROM CompanySpeciality WHERE speciality_id = :id")
    fun getBySpecialityId(id: Int): CompanySpeciality
}