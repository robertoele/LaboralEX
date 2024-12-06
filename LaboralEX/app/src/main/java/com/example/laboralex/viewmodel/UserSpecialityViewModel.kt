package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.dao.UserSpecialityDao

class UserSpecialityViewModel(private val userSpecialityDao: UserSpecialityDao) : ViewModel() {
    suspend fun getUserSpecialities(id: Int) = userSpecialityDao.getByUserId(id)
}