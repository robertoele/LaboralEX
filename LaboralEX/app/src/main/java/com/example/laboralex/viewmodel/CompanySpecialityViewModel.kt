package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanySpecialityDao
import kotlinx.coroutines.launch

class CompanySpecialityViewModel(private val companySpecialityDao: CompanySpecialityDao): ViewModel() {
    fun getCompanySpecialities(id: Int) {
      viewModelScope.launch { companySpecialityDao.getByCompanyId(id) }
    }
}