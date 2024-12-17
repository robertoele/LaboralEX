package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Speciality::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("specialityId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class UserSpeciality(
    @PrimaryKey val id: Long,
    @ColumnInfo("userId") val userId: Long,
    @ColumnInfo("specialityId") val specialityId: Long,
)
