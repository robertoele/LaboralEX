package com.example.laboralex.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.laboralex.database.entity.CompanySkill
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanySkillDao {
    @Insert
    suspend fun insertAll(vararg companies: CompanySkill)

    @Query("SELECT * FROM CompanySkill")
    fun getAllAsFlow(): Flow<List<CompanySkill>>
}