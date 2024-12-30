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
            entity = Skill::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("skillId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class UserSkill(
    @ColumnInfo("userId") val userId: Long,
    @ColumnInfo("skillId") val skillId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
