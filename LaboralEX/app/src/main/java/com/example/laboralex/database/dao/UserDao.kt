package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User): Long

    @Insert
    suspend fun insertAll(vararg users: User): List<Long>

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User")
    fun getAllAsFlow(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE uid = :id")
    suspend fun getById(id: Long): User
}