package com.example.laboralex.database.entity.company

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.laboralex.database.entity.Skill

data class CompanyWithSkills(
    @Embedded val company: Company,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "skillId",
        associateBy = Junction(CompanySkill::class)
    )
    val skills: List<Skill>
)
