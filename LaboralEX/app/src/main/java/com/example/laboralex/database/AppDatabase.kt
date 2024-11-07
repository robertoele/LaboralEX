package com.example.laboralex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySpecialityDao
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.dao.UserSpecialityDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySpeciality
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.database.entity.User
import com.example.laboralex.database.entity.UserSpeciality

@Database(entities = [Company::class, CompanySpeciality::class, Speciality::class, User::class, UserSpeciality::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun CompanyDao(): CompanyDao
    abstract fun SpecialityDao(): SpecialityDao
    abstract fun companySpecialityDao(): CompanySpecialityDao
    abstract fun UserDao(): UserDao
    abstract fun UserSpecialityDao(): UserSpecialityDao
}