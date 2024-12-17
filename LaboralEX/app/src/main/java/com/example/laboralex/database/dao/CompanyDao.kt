package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Company

@Dao
interface CompanyDao {
    @Insert
    suspend fun insertAll(vararg companies: Company)

    @Delete
    suspend fun delete(company: Company)

    @Query("SELECT * FROM Company")
    suspend fun getAll(): List<Company>

    @Query("SELECT * FROM Company WHERE id = :id")
    suspend fun getById(id: Long): Company
}