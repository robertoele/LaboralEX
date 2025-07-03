package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    @ColumnInfo("name") val name: String,
    @PrimaryKey(autoGenerate = true) val skillId: Long = 0
) {
    override fun toString(): String {
        return name
    }

    fun notInDB() = skillId.compareTo(0) == 0
}
