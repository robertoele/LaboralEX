package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Company

@Dao
interface CompanyDao {
    @Insert
    fun insertAll(vararg companies: Company)

    @Delete
    fun delete(company: Company)

    @Query("SELECT * FROM Company")
    fun getAll(): List<Company>

    @Query("SELECT * FROM Company WHERE id = :id")
    fun getById(id: Int): Company
}