package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySpecialityDao
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySpeciality
import com.example.laboralex.database.entity.Speciality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val specialityDao: SpecialityDao,
    private val companySpecialityDao: CompanySpecialityDao
) :
    ViewModel() {
    private val _companiesAdded = MutableStateFlow<List<Company>>(emptyList())
    val companiesAdded: StateFlow<List<Company>> = _companiesAdded

    private val _companySkills = MutableStateFlow<List<CompanySpeciality>>(emptyList())
    val companySkills: StateFlow<List<CompanySpeciality>> = _companySkills

    val allSkills = mutableListOf<Speciality>()

    init {
        viewModelScope.launch {
            allSkills.addAll(specialityDao.getAll())

            companyDao.getCompaniesAsFlow().collect {
                _companiesAdded.value = it
            }

            companySpecialityDao.getAllAsFlow().collect { skills ->
                _companySkills.value = skills
            }
        }
    }
}