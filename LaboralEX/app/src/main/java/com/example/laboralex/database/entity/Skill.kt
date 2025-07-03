package com.example.laboralex.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    @ColumnInfo("name") val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    override fun toString(): String {
        return name
    }

    fun notInDB() = id.compareTo(0) == 0
}
