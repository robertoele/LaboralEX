package com.example.laboralex.database.entity.course

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.laboralex.database.entity.Skill

data class CourseWithSkills(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "skillId",
        associateBy = Junction(CourseSkill::class)
    )
    val skills: List<Skill>
)
