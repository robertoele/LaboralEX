package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.Application
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {
    @Insert
    suspend fun insertAll(vararg companies: Application): List<Long>

    @Insert
    suspend fun insert(company: Application): Long

    @Delete
    suspend fun delete(company: Application)

    @Query("SELECT * FROM Application")
    suspend fun getAll(): List<Application>

    @Query("SELECT * FROM Application")
    fun getAsFlow(): Flow<List<Application>>

    @Query("SELECT * FROM Application WHERE id = :id")
    suspend fun getById(id: Long): Application
}