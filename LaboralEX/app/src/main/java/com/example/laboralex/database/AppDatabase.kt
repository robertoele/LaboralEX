package com.example.laboralex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySkillDao
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySkill
import com.example.laboralex.database.entity.Course
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.database.entity.User

@Database(
    entities = [Company::class, CompanySkill::class, Skill::class, User::class, Course::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CompanyDao(): CompanyDao
    abstract fun SkillDao(): SkillDao
    abstract fun CompanySkillDao(): CompanySkillDao
    abstract fun UserDao(): UserDao
    abstract fun CourseDao(): CourseDao
}