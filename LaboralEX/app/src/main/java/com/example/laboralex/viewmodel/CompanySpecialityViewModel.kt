package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanySpecialityDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanySpecialityViewModel @Inject constructor(private val companySpecialityDao: CompanySpecialityDao) :
    ViewModel() {
    fun getCompanySpecialities(id: Int) {
        viewModelScope.launch { companySpecialityDao.getByCompanyId(id) }
    }
}