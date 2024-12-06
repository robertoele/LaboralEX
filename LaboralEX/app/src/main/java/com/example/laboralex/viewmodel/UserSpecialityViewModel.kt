package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.dao.UserSpecialityDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class UserSpecialityViewModel @Inject constructor(private val userSpecialityDao: UserSpecialityDao) :
    ViewModel() {
    suspend fun getUserSpecialities(id: Int) = userSpecialityDao.getByUserId(id)
}