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
            childColumns = arrayOf("companyId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Application(
    @ColumnInfo("requestDate") val requestDate: String,
    @ColumnInfo("state") val state: Int,
    @ColumnInfo("companyId") val companyId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    enum class ApplicationState { OPENED, DENIED }
}