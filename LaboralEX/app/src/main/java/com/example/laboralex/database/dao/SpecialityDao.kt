package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Speciality

@Dao
interface SpecialityDao {
    @Insert
    suspend fun insertAll(vararg specialities: Speciality)
    @Delete
    suspend fun delete(speciality: Speciality)

    @Query("SELECT * FROM Speciality")
    suspend fun getAll(): List<Speciality>

    @Query("SELECT * FROM Speciality WHERE id = :id")
    suspend fun getById(id: Int): List<Speciality>
}