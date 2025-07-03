package com.example.laboralex.database.entity.company

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["companyId", "skillId"])
data class CompanySkill(@ColumnInfo val companyId: Long, @ColumnInfo val skillId: Long)
