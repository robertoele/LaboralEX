package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.UserSpeciality

@Dao
interface UserSpecialityDao {
    @Insert
    fun insertAll(vararg companies: UserSpeciality)

    @Delete
    fun delete(company: UserSpeciality)

    @Query("SELECT * FROM UserSpeciality")
    fun getAll(): List<UserSpeciality>

    @Query("SELECT * FROM UserSpeciality WHERE specialityId = :id")
    fun getBySpecialityId(id: Int): UserSpeciality

    @Query("SELECT * FROM UserSpeciality WHERE userId = :id")
    fun getByUserId(id: Int): UserSpeciality
}