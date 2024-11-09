package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.UserSpeciality

@Dao
interface UserSpecialityDao {
    @Insert
    suspend fun insertAll(vararg companies: UserSpeciality)

    @Delete
    suspend fun delete(company: UserSpeciality)

    @Query("SELECT * FROM UserSpeciality")
    suspend fun getAll(): List<UserSpeciality>

    @Query("SELECT * FROM UserSpeciality WHERE specialityId = :id")
    suspend fun getBySpecialityId(id: Int): UserSpeciality

    @Query("SELECT * FROM UserSpeciality WHERE userId = :id")
    suspend fun getByUserId(id: Int): UserSpeciality
}