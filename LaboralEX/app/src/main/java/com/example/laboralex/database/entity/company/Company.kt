package com.example.laboralex.database.entity.company

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @ColumnInfo("name") val name: String,
    @PrimaryKey(autoGenerate = true) val companyId: Long = 0
)
