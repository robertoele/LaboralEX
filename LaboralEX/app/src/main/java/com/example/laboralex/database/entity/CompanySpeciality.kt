package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("company_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Speciality::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("speciality_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CompanySpeciality(
    @PrimaryKey val id: Long,
    @ColumnInfo("company_id") val companyId: Long,
    @ColumnInfo("speciality_id") val specialityId: Long
)
