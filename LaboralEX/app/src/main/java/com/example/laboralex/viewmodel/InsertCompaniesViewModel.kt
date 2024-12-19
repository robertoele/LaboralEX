package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    private val companyDao: CompanyDao
) : ViewModel() {

    val companiesDisplayed = mutableMapOf<Company, List<Speciality>>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun saveCompany() {
        viewModelScope.launch {
            val id = companyDao.insert(Company(name = name.value))
            val companyInserted = Company(id = id, name = name.value)
        }
    }
}