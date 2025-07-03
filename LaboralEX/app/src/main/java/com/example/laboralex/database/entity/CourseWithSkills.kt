package com.example.laboralex.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CourseWithSkills(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "skillId",
        associateBy = Junction(CourseSkill::class)
    )
    val skills: List<Skill>
)
